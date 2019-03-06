//The SequenceHandler is the piece of code that defines the sequence of events
//that constitute the experiment.
//
//SequenceHandler.Next() will run the next step in the sequence.
//
//We can also switch between the main sequence of events and a subsequence
//using the SequenceHandler.SetLoop command. This takes two inputs:
//The first sets which loop we are in. 0 is the main loop. 1 is the first
//subloop. 2 is the second subloop, and so on.
//
//The second input is a Boolean. If this is set to true we initialise the 
//position so that the sequence will start from the beginning. If it is
//set to false, we will continue from whichever position we were currently in.
//
//So SequenceHandler.SetLoop(1,true) will switch to the first subloop,
//starting from the beginning.
//
//SequenceHandler.SetLoop(0,false) will switch to the main loop,
//continuing from where we left off.

package com.sam.webtasks.client;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.basictools.CheckIdExists;
import com.sam.webtasks.basictools.CheckScreenSize;
import com.sam.webtasks.basictools.ClickPage;
import com.sam.webtasks.basictools.Consent;
import com.sam.webtasks.basictools.Counterbalance;
import com.sam.webtasks.basictools.Finish;
import com.sam.webtasks.basictools.InfoSheet;
import com.sam.webtasks.basictools.Initialise;
import com.sam.webtasks.basictools.PHP;
import com.sam.webtasks.basictools.Slider;
import com.sam.webtasks.basictools.TimeStamp;
import com.sam.webtasks.iotask1.IOtask1Block;
import com.sam.webtasks.iotask1.IOtask1BlockContext;
import com.sam.webtasks.iotask1.IOtask1DisplayParams;
import com.sam.webtasks.iotask1.IOtask1InitialiseTrial;
import com.sam.webtasks.iotask1.IOtask1RunTrial;
import com.sam.webtasks.iotask2.IOtask2Block;
import com.sam.webtasks.iotask2.IOtask2BlockContext;
import com.sam.webtasks.iotask2.IOtask2RunTrial;
import com.sam.webtasks.iotask2.IOtask2Feedback;
import com.sam.webtasks.iotask2.IOtask2InitialiseTrial;
import com.sam.webtasks.iotask2.IOtask2PreTrial;

public class SequenceHandler {
	public static void Next() {	
		// move forward one step in whichever loop we are now in
		sequencePosition.set(whichLoop, sequencePosition.get(whichLoop) + 1);

		switch (whichLoop) {
		case 0: // MAIN LOOP
			switch (sequencePosition.get(0)) {
			/***********************************************************************
			 * The code here defines the main sequence of events in the experiment *
			 ********************************************************************/
			case 1:
				ClickPage.Run(Instructions.Get(0),  "Next");
				break;
			
			case 2:	
				IOtask2Block block1 = new IOtask2Block();
				//display running total of points on the screen
				block1.showLivePoints=false; 
		
				block1.totalCircles=6;
				block1.nTargets=0;
				block1.nTrials=2;
				//different coloured circles can be worth different numbers of points
				block1.variablePoints = true;
				
				//points associated with bottom, left, right, and top corners of the box
				//if the left, right, or top is set to zero points it will be shown in black
				//and no targets will be assigned to that side
				block1.pointValues = new int[] {0,1,1,0};
				
				//lockout for setting reminders
				block1.reminderLockout=false;
				block1.reminderLockoutTime=2000;
				block1.Run();
				break;
			case 3:
				ClickPage.Run(Instructions.Get(1),  "Next");
			    break;
			
			case 4:
				IOtask2Block block2 = new IOtask2Block();
				block2.showLivePoints=false; 
				block2.totalCircles=8;
				block2.nTargets=2;
				block2.nTrials=2;
				block2.variablePoints = true;
				block2.pointValues = new int[] {0,1,1,0};
				block2.reminderLockout=false;
				block2.reminderLockoutTime=2000;
				
				//if (IOtask2BlockContext.showPostTrialFeedback()) {
					//IOtask2Feedback.Run();
				//} else {
					//SequenceHandler.Next();
				//}
				//block2.showPostTrialFeedback = true;				
				block2.Run();
				
				break;
			
			/*case 5:
				IOtask2Block fbblock = IOtask2BlockContext.getContext();
				if (fbblock.nHits == 0) {
					msg = "You did not get any special circles correct this time.";
				} else if (block.nHits <= (block.nTargets/2)) {
					msg = "Well done - good work! You are responding well to the special circles.";
				} else if (block.nHits == block.nTargets) {
					msg = "Well done - perfect! You responded correctly to all of the special circles.";
				} else {
					msg = "Well done - excellent work! You responded correctly to most of the special circles.";
				}
				*/
			    
			/* try 1
			 * case 5:
				IOtask2Block fbblock2 = new IOtask2Feedback.Run();
				if (IOtask2BlockContext.showPostTrialFeedback()) {
					IOtask2Feedback.Run();
				} else {
					SequenceHandler.Next();
				}
				break;*/
				
			/* try2
			case 5:
				public static 
				String msg="";
				int nHits = IOtask2BlockContext.getnHits();
				if (nHits == 0) {
					msg = "You did not get any special circles correct this time.";
				} else if (nHits <= (block2.nTargets/2)) {
					msg = "Well done - good work! You are responding well to the special circles.";
				} else if (nHits == block2.nTargets) {
					msg = "Well done - perfect! You responded correctly to all of the special circles.";
				} else {
					msg = "Well done - excellent work! You responded correctly to most of the special circles.";
				}
				*/
				
			//  try 3  my code for feedback loop
			
			  case 5:
				int points = IOtask2BlockContext.getTotalPoints();
				if (points == 0) {
					ClickPage.Run(Instructions.Get(2),  "Try again");
					SequenceHandler.SetLoop(0,  false);  //how to return to last step - block2?
				  
				} 
				  else if (points == IOtask2BlockContext.maxPoints()) {
					ClickPage.Run(Instructions.Get(6),  "Next");
					SequenceHandler.SetLoop(0,  false);
			
				} 
				  else {
					ClickPage.Run(Instructions.Get(5),  "Next");
					SequenceHandler.SetLoop(0,  false);
			
				}
					
				
				break;
				
			case 6:
				ClickPage.Run(Instructions.Get(3),  "Next");
				break;
				
			case 7:// need add feedback 
				IOtask2Block block3 = new IOtask2Block();
				block3.showLivePoints=false; 
				block3.totalCircles=25;
				block3.nTargets=16;	
				block3.nTrials=2;
				block3.variablePoints = true;
				block3.pointValues = new int[] {0,1,1,0};
				block3.reminderLockout=false;
				block3.reminderLockoutTime=2000;
				block3.Run();
			    break;
			    
			/*case 8: //one of the feedback
				ClickPage.Run(Instructions.Get(5),  "Next");
				break;*/
			
		 case 8:
				int points2 = IOtask2BlockContext.getTotalPoints();
				if (points2 == 0) {
					ClickPage.Run(Instructions.Get(2),  "Try again");
					SequenceHandler.SetLoop(0,  false);  //how to return to last step - block2?
				  
				} 
				  else if (points2 == IOtask2BlockContext.maxPoints()) {
					ClickPage.Run(Instructions.Get(6),  "Next");
					SequenceHandler.SetLoop(0,  false);
			
				} 
				  else {
					ClickPage.Run(Instructions.Get(5),  "Next");
					SequenceHandler.SetLoop(0,  false);
			
				}
					
				
				break;
				
			
			case 9:
				ClickPage.Run(Instructions.Get(7),  "Next");
				break;
			
			case 10:
				IOtask2Block block4 = new IOtask2Block();
				block4.showLivePoints=false; 
				block4.totalCircles=25;
				block4.nTargets=8;	
				block4.nTrials=2;
				block4.variablePoints = true;
				block4.pointValues = new int[] {0,1,1,0};
				block4.reminderLockout=false;
				block4.reminderLockoutTime=2000;
				block4.Run();
			    break;
			    
			case 11:
				ClickPage.Run(Instructions.Get(8),  "Next");
				break;
			
			case 12:
				IOtask2Block block5 = new IOtask2Block();
				block5.showLivePoints=false; 
				block5.totalCircles=25;
				block5.nTargets=8;	
				block5.nTrials=2;
				block5.variablePoints = true;
				block5.pointValues = new int[] {0,1,1,0};
				block5.reminderLockout=false;
				block5.reminderLockoutTime=2000;
				block5.Run();
			    break;
			
			    
			case 13:
				int points3 = IOtask2BlockContext.getTotalPoints();
				if (points3 == 0) {
					ClickPage.Run(Instructions.Get(2),  "Try again");
					SequenceHandler.SetLoop(0,  false);  //how to return to last step - block2?
				  
				} 
				  else if (points3 == IOtask2BlockContext.maxPoints()) {
					ClickPage.Run(Instructions.Get(6),  "Next");
					SequenceHandler.SetLoop(0,  false);
			
				} 
				  else {
					ClickPage.Run(Instructions.Get(5),  "Next");
					SequenceHandler.SetLoop(0,  false);
			
				}
				
				break;
		    
			case 14:
				ClickPage.Run(Instructions.Get(10),  "Next");
				break;
			
			case 15:
				IOtask2Block block6 = new IOtask2Block();
				block6.showLivePoints=true; 
				block6.totalCircles=25;
				block6.nTargets=8;	
				block6.nTrials=2;
				block6.variablePoints = true;
				block6.pointValues = new int[] {0,10,1,0};
				block6.reminderLockout=true;
				block6.reminderLockoutTime=2000;
				block6.Run();
			    break;
			 
			case 16:
				ClickPage.Run(Instructions.Get(11),  "Start Experiment");
				break;
			
			case 17:
				IOtask2Block block7 = new IOtask2Block();
				block7.showLivePoints=true; 
				block7.totalCircles=25;
				block7.nTargets=8;	
				block7.nTrials=1;
				block7.variablePoints = true;
				block7.pointValues = new int[] {0,10,1,0};
				block7.reminderLockout=true;
				block7.reminderLockoutTime=2000;
				block7.Run();
			    break;
			 
			case 18:
				Finish.Run();
				break;
				   
			}
			break;
		

		/********************************************/
		/* no need to edit the code below this line */
		/********************************************/

		case 1: // initialisation loop
			switch (sequencePosition.get(1)) {
			case 1:
				// initialise experiment settings
				Initialise.Run();
				break;
			case 2:
				// make sure that a participant ID has been registered.
				// If not, the participant may not have accepted the HIT
				CheckIdExists.Run();
				break;
			case 3:
				// check the status of this participant ID.
				// have they already accessed or completed the experiment? if so,
				// we may want to block them, depending on the setting of
				// SessionInfo.eligibility
				PHP.CheckStatus();
				break;
			case 4:
				// check whether this participant ID has been used to access a previous experiment
				PHP.CheckStatusPrevExp();
				break;
			case 5:
				// clear screen, now that initial checks have been done
				RootPanel.get().clear();

				// make sure the browser window is big enough
				CheckScreenSize.Run(SessionInfo.minScreenSize, SessionInfo.minScreenSize);
				break;
			case 6:
				if (SessionInfo.runInfoConsentPages) { 
					InfoSheet.Run(Instructions.InfoText());
				} else {
					SequenceHandler.Next();
				}
				break;
			case 7:
				if (SessionInfo.runInfoConsentPages) { 
					Consent.Run();
				} else {
					SequenceHandler.Next();
				}
				break;
			case 8:
				SequenceHandler.SetLoop(0, true); // switch to and initialise the main loop
				SequenceHandler.Next(); // start the loop
				break;
			}
			break;
			
		case 2: // IOtask1 loop
			switch (sequencePosition.get(2)) {
			/*************************************************************
			 * The code here defines the sequence of events in subloop 2 *
			 * This runs a single trial of IOtask1                       *
			 *************************************************************/
			case 1:
				// first check if the block has ended. If so return control to the main sequence
				// handler
				IOtask1Block block = IOtask1BlockContext.getContext();

				if (block.currentTrial == block.nTrials) {
					SequenceHandler.SetLoop(0, false);
				}

				SequenceHandler.Next();
				break;
			case 2:
				// now initialise trial and present instructions
				IOtask1InitialiseTrial.Run();
				break;
			case 3:
				// now run the trial
				IOtask1RunTrial.Run();
				break;
			case 4:
				// we have reached the end, so we need to restart the loop
				SequenceHandler.SetLoop(2, true);
				SequenceHandler.Next();
				break;
				// TODO: mechanism to give post-trial feedback?
				
			}
			break;
			
		case 3: //IOtask2 loop
			switch (sequencePosition.get(3)) {
			/*************************************************************
			 * The code here defines the sequence of events in subloop 3 *
			 * This runs a single trial of IOtask2                       *
			 *************************************************************/
			case 1:
				// first check if the block has ended. If so return control to the main sequence
				// handler
				IOtask2Block block = IOtask2BlockContext.getContext();
				
				if (block.currentTrial == block.nTrials) {
					SequenceHandler.SetLoop(0,  false);
				}
				
				SequenceHandler.Next();
				break;
			case 2:
				IOtask2InitialiseTrial.Run();
				break;
			case 3:
				//present the pre-trial choice if appropriate
				if (IOtask2BlockContext.currentTargetValue() > -1) {
					IOtask2PreTrial.Run();
				} else { //otherwise just skip to the start of the block
					SequenceHandler.Next();
				}
				break;
			case 4:
				//now run the trial
				IOtask2RunTrial.Run();
				break;
			case 5:
				if (IOtask2BlockContext.showPostTrialFeedback()) {
					IOtask2Feedback.Run();
				} else {
					SequenceHandler.Next();
				}
				break;
			case 6:
				//we have reached the end, so we need to restart the loop
				SequenceHandler.SetLoop(3,  true);
				SequenceHandler.Next();
				break;
		
		 
				
			   
			
			}
		
		}
	}
	
	private static ArrayList<Integer> sequencePosition = new ArrayList<Integer>();
	private static int whichLoop;

	public static void SetLoop(int loop, Boolean init) {
		whichLoop = loop;

		while (whichLoop + 1 > sequencePosition.size()) { // is this a new loop?
			// if so, initialise the position in this loop to zero
			sequencePosition.add(0);
		}

		if (init) { // go the beginning of the sequence if init is true
			sequencePosition.set(whichLoop, 0);
		}
	}

	// set a new position
	public static void SetPosition(int newPosition) {
		sequencePosition.set(whichLoop, newPosition);
	}

	// get current position
	public static int GetPosition() {
		return (sequencePosition.get(whichLoop));
	}
}
