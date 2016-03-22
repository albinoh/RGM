package org.ahernandez.rgm;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a line read from the system in. The object returned identifies the type of operation
 * that the Pricer will execute
 * 
 * @author ahernandez
 *
 */
public class Action {
	
	private int mActionType = 0;
	private String mOrder;
	private BigDecimal mValue;
	private int mSize;
	private int mTimeStamp;
	private int mOrderType;
	
	private static final int TIME = 1;
	private static final int ORDER_TYPE = 2;
	private static final int ORDER_ID = 3;
	private static final int REDUCE_AMT = 5;
	private static final int SELL_BUY = 6;
	private static final int PRICE = 7;
	private static final int SELL_BUY_AMT = 8;
	
	private static final Pattern p = Pattern.compile("(\\d{8})\\s([AR])\\s([a-z]+)\\s((\\d+)|([SB])\\s(\\d*\\.\\d*)\\s(\\d+))");

	/**
	 * Constructor and parser, if the line provided is incorrect an exception is thrown
	 * 
	 * @param  lAction line expected to contain a single action. 
	 *
	 */
	public Action(String lAction){
		

		Matcher m = p.matcher(lAction);

		while (m.find()) {
			if(m.group(ORDER_TYPE).equals("A")){
				mActionType = 1;
				mTimeStamp = Integer.parseInt(m.group(TIME));
				mOrder = m.group(ORDER_ID);
				mValue = new BigDecimal(m.group(PRICE));
				mSize = Integer.parseInt(m.group(SELL_BUY_AMT));
				if(m.group(SELL_BUY).equals("S")){
					mOrderType=1;
				}else{
					mOrderType=2;
				};
			}else{
				mActionType = 2;
				mTimeStamp = Integer.parseInt(m.group(TIME));
				mOrder = m.group(ORDER_ID);
				mValue = null;
				mSize = Integer.parseInt(m.group(REDUCE_AMT));
			}
		}
		if(mActionType == 0){
			throw new IllegalArgumentException("Error generating Action with format: "+lAction);
		}
	}
	
	public int getActionTimeStamp(){
		return mTimeStamp;
	}
	
	public int getActionType(){
		return mActionType;
	}
	
	public String getOrderID(){
		return mOrder;
	}
	
	public int getOrderSize(){
		return mSize;
	}
	
	public BigDecimal getOrderPrice(){
		return mValue;
	}
	
	public int getOrderType(){
		return mOrderType;
	}
	
	public String toString(){
		return "Type" + mActionType + "Size: " +mSize;
		
	}
}
