package frc.robot.libraries;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

class PixyI2C {

	// Default address of Pixy Camera. You can change the address of the Pixy in
	// Pixymon under setting-> Interface
	private static final int PIXY_I2C_DEFAULT_ADDR = 0x54;

	// Communication/misc parameters
	private static final int PIXY_INITIAL_ARRAYSIZE = 30;
	private static final int PIXY_MAXIMUM_ARRAYSIZE = 130;
	private static final int PIXY_START_WORD = 0xaa55; // for regular color recognition
	private static final int PIXY_START_WORD_CC = 0xaa56; // for color code - angle rotation recognition
	private static final int PIXY_START_WORDX = 0x55aa; // regular color another way around
	private static final int PIXY_MAX_SIGNATURE = 7;
	private static final int PIXY_DEFAULT_ARGVAL = 0xffff;

	// Pixy x-y position values
	private static final int PIXY_MIN_X = 0; // x: 0~319 pixels, y:0~199 pixels. (0,0) starts at bottom left
	private static final int PIXY_MAX_X = 319;
	private static final int PIXY_MIN_Y = 0;
	private static final int PIXY_MAX_Y = 199;

	private enum BlockType {

		NORMAL_BLOCK, // normal color recognition
		CC_BLOCK // color-code(change in angle) recognition

	}

	boolean skipStart; // skips to check 0xaa55, which is byte that tells pixy it is start of new frame
	int blockCount; // How many signatured objects are there?
	int blockArraySize; // not used in the code
	Block[] blocks = new Block[100]; // array that stores blockCount array

	I2C i2c; // Declare i2c
	// SmartDashboard dash = new SmartDashboard();
	BlockType blockType;// it is the enum on the top

	private class Block {
		int signature; // Identification number for your object - you could set it in the pixymon
		int x; // 0 - 320
		int y; // 0 - 200
		int width;
		int height;
		int angle;

		// print block structure!
		void print() {
			int i, j;
			char[] buf = new char[128];
			char[] sig = new char[6];
			char d;
			boolean flag;
    
			if (signature > PIXY_MAX_SIGNATURE) {
				// color code! (CC)
				// convert signature number to an octal string
				for (i=12, j=0, flag=false; i>=0; i-=3) { //assigns value to signature, x, y, width, height, and anlge
				
						d = (char) ((signature >> i) & 0x07);
						if ((d > 0) && (!flag)) {
							flag = true;
						}

						if (flag) {
							sig[j++] = d; //d + '0'
						}
					}
				
					sig[j] = '\0';
				
					//Check if this is really necessary;
					//SmartDashboard.putData("PixyI2C", );
					SmartDashboard.putString(key, value)
					//System.out.println("CC block! sig: %s (%d decimal) x: %d y: %d width: %d height: %d angle %d", sig, signature, x, y, width, height, angle);
					System.out.println("CC block! sig: " + sig + "(" + signature + "decimal) x: " + x + " y: " + y + " width: " + width
					+ " height: " + height + " angle: " + angle);
				}
			
			else {
				System.out.println("sig: " + sig + "(" + signature + "decimal) x: " + x + " y: " + y + " width: " + width
				+ " height: " + height); // regular block.  Note, angle is always zero, so no need to print
				//System.out.println("sig %d x: %d y: %d width: %d height: %d", signature, x, y, width, height); //prints out data to console instead of smartDashboard -> check on the side of the driver station, check +print and click view console
				System.out.println(buf);
			}
    	}
	}

	public void init() {
		i2c = new I2C(I2C.Port.kOnboard, PIXY_I2C_DEFAULT_ADDR); // Initializing i2c;
	}

	// checks whether if it is start of the normal frame, CC frame, or the data is
	// out of sync
	public boolean getStart() {
		int w, lastw;
		lastw = 0xFFFF;

		while (true) {

			w = getWord();

			if (w == 0 && lastw == 0) {
				try{
					Thread.sleep(1);
				}
				catch(Exception e){}
				// delayMicroseconds(10);
				return false;
			} else if (w == PIXY_START_WORD && lastw == PIXY_START_WORD) {
				blockType = BlockType.NORMAL_BLOCK;
				return true;
			}

			else if (w == PIXY_START_WORD_CC && lastw == PIXY_START_WORD) {
				blockType = BlockType.CC_BLOCK;
				return true;
			}

			else if (w == PIXY_START_WORDX) { // when byte recieved was 0x55aa instead of otherway around, the code
												// syncs the byte
				System.out.println("Pixy: reorder");
				getByte(); // resync
			}

			lastw = w;
		}
	}

	// Getting two Bytes from Pixy (The full information)
	int getWord() {
		byte[] buffer = { 0, 0 };

		i2c.readOnly(buffer, 2);
		return (buffer[1] << 8) | buffer[0]; // shift buffer[1] by 8 bits and add( | is bitwise or) buffer[0] to it
	}

	int getByte() {
		byte[] buffer = { 0 };
		i2c.readOnly(buffer, 1);
		return buffer[0];
	}

	int getBlocks(int maxBlocks) {
		blocks[0] = new Block(); //resets the array - clears out data from previous reading
		int i;
		int w, checksum, sum;
		Block block;

		if (!skipStart) {
			if (false == getStart()){
				return 0;
			}
			else {
				skipStart = false;
			}

			for(blockCount = 0; (blockCount < maxBlocks) && (blockCount < PIXY_MAXIMUM_ARRAYSIZE);) {
				checksum = getWord();
				if (checksum == PIXY_START_WORD) {// we've reached the beginning of the next frame - checking for 0xaa55
					skipStart = true; //starts this function
					blockType = BlockType.NORMAL_BLOCK;
					//Serial.println("skip");
					return blockCount;
				}
				else if (checksum == PIXY_START_WORD_CC) {//we've reacehd the beginning of the next frame - checking for 0xaa56
					skipStart = true;
					blockType = BlockType.CC_BLOCK;
					return blockCount;
				}
				else if (checksum == 0) {
					return blockCount;
				}

				//if (blockCount>blockArraySize)
					//resize();

				block = blocks[blockCount];

				for (i = 0, sum = 0; i < blocks.length; i++) {
					if ((blockType == BlockType.NORMAL_BLOCK) && (i>=5)) {// skip --if not an CC block, no need to consider angle
					
						block.angle = 0;
						break;
					}

					w = getWord();
					sum += w; //sum = w + sum
					
					*((uint16_t *)block + i) = w; //converts block to interger value
				}

				if (checksum == sum) {
					blockCount++;
				}
				else {
					printf("Pixy: cs error");
				}

				w = getWord(); //when this is start of the frame
				if (w == PIXY_START_WORD) {
					blockType = NORMAL_BLOCK;
				}
				else if(w == PIXY_START_WORD_CC) {
					blockType = CC_BLOCK;
				}
				else {
					return blockCount;
				}
			}
		}
	}
}

/*
 * int getBlocks(int maxBlocks) { blocks[0] = {0}; //resets the array - clears
 * out data from previous reading uint8_t i; uint16_t w, checksum, sum; Block
 * *block;
 * 
 * if (!skipStart) //when computer has not seen 0xaa55 (starting frame) { if
 * (getStart()==false) return 0; } else skipStart = false;
 * 
 * for(blockCount=0; blockCount<maxBlocks && blockCount<PIXY_MAXIMUM_ARRAYSIZE;)
 * { checksum = getWord(); if (checksum==PIXY_START_WORD) // we've reached the
 * beginning of the next frame - checking for 0xaa55 { skipStart = true;
 * //starts this function blockType = NORMAL_BLOCK; //Serial.println("skip");
 * return blockCount; } else if (checksum==PIXY_START_WORD_CC) //we've reacehd
 * the beginning of the next frame - checking for 0xaa56 { skipStart = true;
 * blockType = CC_BLOCK; return blockCount; } else if (checksum==0) return
 * blockCount;
 * 
 * //if (blockCount>blockArraySize) //resize();
 * 
 * block = blocks + blockCount;
 * 
 * for (i=0, sum=0; i<sizeof(Block)/sizeof(uint16_t); i++) { if
 * (blockType==NORMAL_BLOCK && i>=5) // skip --if not an CC block, no need to
 * consider angle { block->angle = 0; break; } w = getWord(); sum += w; //sum =
 * w + sum ((uint16_t *)block + i) = w; //converts block to interger value } if
 * (checksum==sum) blockCount++; else printf("Pixy: cs error");
 * 
 * w = getWord(); //when this is start of the frame if (w==PIXY_START_WORD)
 * blockType = NORMAL_BLOCK; else if (w==PIXY_START_WORD_CC) blockType =
 * CC_BLOCK; else return blockCount; } }
 * 
 * 
 * void followBlock() //change servo into drive code, and it should work well :)
 * { if(blocks->signature == 1) //if pixy identify object 1 { if(blocks->x >
 * 160) //if the object is on the right of the pixy (160 is about the middle) {
 * servo->Set(180); // servo goes to the left -- this may vary depending on the
 * servo } else if(blocks->x < 320 || blocks->x > 160) //if the pixy is on the
 * left of the pixy { servo->Set(0); //servo goes to the right } } }
 * 
 * int increment_since_last_find = 0;
 * 
 * /* void TeleopPeriodic() { int lastX; int lastY; uint16_t numBlocks =
 * getBlocks(100); //Retrieve up to 100 objects, record the num of objects found
 * in "numBlocks"
 * 
 * //The following code sprays data to the Driver Station for debug purposes
 * only printf("blocks: "); printf("%d", numBlocks);printf(" "); //prints number
 * of block to the console blocks[0].print(); // prints x, y, width, and etc. to
 * the console (the vairables in the block object) printf(" "); //new
 * line(space)
 * 
 * // Code here checks numBlocks and updates last known x and y
 * increment_since_last_find++; if(numBlocks > 0){ lastX = blocks[0].x; lastY =
 * blocks[0].y; //Show on Smart Dashboard
 * SmartDashboard::PutNumber("Block @ X = ",lastX);
 * SmartDashboard::PutNumber("Block @ Y = ",lastY); increment_since_last_find =
 * 0; }
 * 
 * //Display how old our data is
 * SmartDashboard::PutNumber("Inc since last update",increment_since_last_find);
 * 
 * //This doesn't work! ergh...
 * SmartDashboard::PutBoolean("Check Address Result",i2c->AddressOnly());
 * 
 * }
 * 
 * 
 * void TestPeriodic() {
 * 
 * } }
 * 
 * START_ROBOT_CLASS(Robot)
 */