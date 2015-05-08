package hkust.cse.calendar.unit;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeMac{
    private static int mYear;
    private static int mMonth;
    private static int mDate;
    private static int mHour;
    private static int mMinute;
    private static int mSecond;
    private static boolean mIsEnable=false;
    private static int mCount=0;
    
    public TimeMac()
    {
            
    }
    
    
    
    public void setTimeMachine(int year, int month, int date, int hour_of_day, int minute, int second){
            mYear = year ;
            mMonth = month -1;
            mDate = date;
            mHour = hour_of_day;
            mMinute = minute;
            mSecond = second;
    }

    
    public void startMachine(){
            
    }
    
    public int getYear(){
            return mYear;
    }
    
    public int getMonth(){
            return mMonth;
    }
    
    public int getDate(){
            return mDate;
    }
    
    public int getHourOfDay(){
            return mHour;
    }
    
    public int getMinute(){
            return mMinute;
    }
    
    public int getSecond(){
            return mSecond;
    }
    
    public int getCount(){
        return mCount;
}
    
    public void turnOn(){
    		mCount++;
            mIsEnable=true;
            Timer timer = new Timer();
    		timer.schedule( new TimerTask() {
    		    public void run() {
    		    	mSecond+=1;
    		    	if(mSecond==60){
    		    		mSecond=0;
    		    		mMinute+=1;
    		    	}
    		    	if(mMinute==60){
    		    		mMinute=0;
    		    		mHour+=1;
    		    	}
    		    	if(mHour==24){
    		    		mHour=0;
    		    		mDate+=1;
    		    	}
    		    	switch(mMonth-1){
    		    		case 1:
    		    			if(mDate==32){
    		    			mDate=1;
    		    			mMonth+=1;
    		    		}
    		    				break;
    		    		case 2:
    		    			if(mYear%4==0){
    		    				if(mDate==30){
    		    					mDate=1;
    		    					mMonth+=1;
    		    				}}
    		    			else{
    		    				if(mDate==29){
    		    					mDate=1;
    		    					mMonth+=1;
    		    				}
    		    			}
        		    				break;
    		    		case 3:
    		    			if(mDate==32){
        		    			mDate=1;
        		    			mMonth+=1;
        		    		}
        		    				break;
    		    		case 4:
    		    			if(mDate==31){
        		    			mDate=1;
        		    			mMonth+=1;
        		    		}
        		    				break;
    		    		case 5:
    		    			if(mDate==32){
        		    			mDate=1;
        		    			mMonth+=1;
        		    		}
        		    				break;
    		    		case 6:
    		    			if(mDate==31){
    		    			mDate=1;
    		    			mMonth+=1;
    		    		}
    		    				break;
    		    		case 7:
    		    			if(mDate==32){
        		    			mDate=1;
        		    			mMonth+=1;
        		    		}
        		    				break;
    		    		case 8:
    		    			if(mDate==32){
        		    			mDate=1;
        		    			mMonth+=1;
        		    		}
        		    				break;
    		    		case 9:
    		    			if(mDate==31){
        		    			mDate=1;
        		    			mMonth+=1;
        		    		}
        		    				break;
    		    		case 10:
    		    			if(mDate==32){
        		    			mDate=1;
        		    			mMonth+=1;
        		    		}
        		    				break;
    		    		case 11:
    		    			if(mDate==31){
        		    			mDate=1;
        		    			mMonth+=1;
        		    		}
        		    				break;
    		    		case 12:
    		    			if(mDate==32){
        		    			mDate=1;
        		    			mMonth+=1;
        		    		}
        		    				break;
    		
    		    	}
    		    	
    		    	if(mMonth==13){
    		    		mMonth=1;
    		    		mYear+=1;
    		    	}
    		    	
    		    }
    		    	}, 0, 1000);
    		    
    }
    public void turnOff(){
            mIsEnable=false;
    }
    
    public boolean isEnable(){
    	if(mIsEnable==true)
    		return true;
        return false;
    }
    

}

