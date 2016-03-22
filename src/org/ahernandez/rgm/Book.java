package org.ahernandez.rgm;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;


/**
 * Contains Bid or Ask Orders. Calculates the current value according to size and the amount of
 * orders in it. 
 * 
 * Implemented using two priority queues that hold the orders that fall within size and all others,
 * orders are removed from the holding queue using a "lazy" approach, orders are not removed 
 * when the size is set to 0 only the current value is recalculated and whenever a order with 
 * size 0 reaches the top of the priority queue it is discarded.  
 * 
 * @author ahernandez
 *
 */
public class Book {
	
	private final PriorityQueue<Order> mCurrentOrders;
	private final PriorityQueue<Order> mPotentialOrders;
	
	private BigDecimal mCurrentPrice;
	private BigDecimal mOverFlow;
	private int mTargetSize;
	private int mCurrentSize;
	private Comparator<Order> mComparator;

	
	private final BigDecimal mDefultValue = new BigDecimal(0);
	
	public Book(Comparator<Order> lComparator, int lTargetSize){
		mCurrentPrice = new BigDecimal(0);
		mOverFlow = new BigDecimal(0);
		mTargetSize = lTargetSize;
		mComparator = lComparator;
		mCurrentOrders = new PriorityQueue<Order>(11, lComparator);
		mPotentialOrders = new PriorityQueue<Order>(11, Collections.reverseOrder(lComparator));

	}
	
	public BigDecimal getCurrentPrice(){
		if(mCurrentSize>= mTargetSize){
			return mCurrentPrice.subtract(mOverFlow);
		}else{
			return mDefultValue;
		}
			
	}
	
	public void addOrderToBook(Order lOrder){
		if((mCurrentSize+lOrder.getOrderSize()) <= mTargetSize){
			mCurrentOrders.add(lOrder);
			lOrder.markInQueue(true);
			mCurrentSize += lOrder.getOrderSize();
			mCurrentPrice = mCurrentPrice.add(lOrder.getOrderPrice().multiply(new BigDecimal(lOrder.getOrderSize())));
			mOverFlow = new BigDecimal(0);
		}else if(mCurrentSize < mTargetSize || mComparator.compare(mCurrentOrders.peek(), lOrder)==-1){
			int lOverflow;
			mCurrentOrders.add(lOrder);
			lOrder.markInQueue(true);
			mCurrentSize += lOrder.getOrderSize();
			mCurrentPrice = mCurrentPrice.add(lOrder.getOrderPrice().multiply(new BigDecimal(lOrder.getOrderSize())));
			while((mCurrentSize-mCurrentOrders.peek().getOrderSize())>=mTargetSize){
				Order lRemoved = mCurrentOrders.poll();
				if(lRemoved.getOrderSize()>0){
					mPotentialOrders.add(lRemoved);
					lRemoved.markInQueue(false);
					mCurrentPrice = mCurrentPrice.subtract(lRemoved.getOrderPrice().multiply(new BigDecimal(lRemoved.getOrderSize())));
					mCurrentSize -= lRemoved.getOrderSize();
				}
			}
			lOverflow = mCurrentSize - mTargetSize;
			mOverFlow = mCurrentOrders.peek().getOrderPrice().multiply(new BigDecimal(lOverflow));
		} else{
			mPotentialOrders.add(lOrder);
			lOrder.markInQueue(false);
		}
	}
	
	public void reduce(Order lOrder, int lReduceSize){
		int lComparison = mComparator.compare(mCurrentOrders.peek(), lOrder);
		if(lComparison<0 || (lComparison == 0 && lOrder.isInQueue())){
			int lOverflow;
			mCurrentPrice = mCurrentPrice.subtract(lOrder.getOrderPrice().multiply(new BigDecimal(lReduceSize)));
			mCurrentSize -= lReduceSize;
			lOverflow = (mCurrentSize - mTargetSize)>0? mCurrentSize - mTargetSize : 0;
			if(lOverflow >0 ){
				mOverFlow = mCurrentOrders.peek().getOrderPrice().multiply(new BigDecimal(lOverflow));
			}else{
				mOverFlow = new BigDecimal(0);
				while(mCurrentSize < mTargetSize && !mPotentialOrders.isEmpty()){
					Order lNewOrder = mPotentialOrders.poll();
					//Discard elements that were set to 0 before
					if(lNewOrder.getOrderSize()>0){
						mCurrentOrders.add(lNewOrder);
						lNewOrder.markInQueue(true);
						mCurrentSize += lNewOrder.getOrderSize();
						mCurrentPrice = mCurrentPrice.add(lNewOrder.getOrderPrice().multiply(new BigDecimal(lNewOrder.getOrderSize())));
						lOverflow = mCurrentSize - mTargetSize; 
						mOverFlow = mCurrentOrders.peek().getOrderPrice().multiply(new BigDecimal(lOverflow));
					}
				}
			}	
		} 
	}
}
