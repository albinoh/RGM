package org.ahernandez.rgm;


import java.math.BigDecimal;

/**
 * Order object that represents a Bid or Ask 
 * 
 * @author ahernandez
 *
 */

public class Order implements Comparable<Order>{
	
	private final String mOrderId;
	
	private int mOrderSize;
	private int mOrderType;
	private BigDecimal mOrderPrice;	
	private boolean mInQueue;
	
	public Order(String mId, int mSize, int mType, BigDecimal mPrice) {
		mOrderId = mId;
		mOrderSize = mSize;
		mOrderType = mType;
		mOrderPrice = mPrice;	
		mInQueue = false;
	}
	
	public Order(Action lAction){
		mOrderId = lAction.getOrderID();
		mOrderSize = lAction.getOrderSize();
		mOrderType = lAction.getOrderType();
		mOrderPrice = lAction.getOrderPrice();
		mInQueue = false;
	}
	
	public String getOrderId(){
		return mOrderId;
	}
	
	public int getOrderSize(){
		return mOrderSize;
	}
	
	public int getOrderType(){
		return mOrderType;
	}
	
	public BigDecimal getOrderPrice(){
		return mOrderPrice;
	}
	
	public void reduceSize(int lReduce){
		mOrderSize = mOrderSize - lReduce;
	}
	
	public void markInQueue(Boolean lInQueue){
		mInQueue= lInQueue;
	}
	
	public boolean isInQueue(){
		return mInQueue;
	}
	
    @Override
    public int compareTo(Order lOrder){
        return mOrderId.compareTo(lOrder.mOrderId);
    }
}
