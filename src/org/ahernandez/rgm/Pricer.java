package org.ahernandez.rgm;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Processes Actions which are added/updated in the BidBook or AskBook according to type. Creates
 * new Orders when necessary or retrieves them from the AllOrders hash map, then the BidBook or 
 * AskBook processes it. The AllOrders HashMap assists in retrieving the Order object and updating 
 * its size.  
 * 
 * @author ahernandez
 *
 */
public class Pricer {
	
	
	private final Map<String, Order> lAllOrders = new HashMap<String, Order>();
	private final BigDecimal mDefultValue = new BigDecimal(0);
	private final String NEW_LINE = System.getProperty("line.separator");
	
	private int mTargetSize;
	private Book mAskBook;
	private BigDecimal mCurrentSellValue = new BigDecimal(0);
	private Book mBidBook;
	private BigDecimal mCurrentBuyValue = new BigDecimal(0);
	
	
	private PrintWriter mWriter;
	
	public Pricer(int lTargetSize, OutputStream lOut){
		mTargetSize = lTargetSize;
		mBidBook = new Book(new BidOrderComparator(), mTargetSize);
		mAskBook = new Book(new AskOrderComparator(), mTargetSize);
		mWriter = new PrintWriter(lOut);	
	}
	
	public void end(){
		mWriter.flush();
		mWriter.close();
	}
	
	public void executeAction(Action lAction){
		if(lAction.getActionType() == 1){
			Order lNewOrder = new Order(lAction);
			if(lAction.getOrderType() ==1){
				mAskBook.addOrderToBook(lNewOrder);
			}else{
				mBidBook.addOrderToBook(lNewOrder);
			}
			lAllOrders.put(lNewOrder.getOrderId(), lNewOrder);
		} else{
			Order lOrder = lAllOrders.get(lAction.getOrderID());
			lOrder.reduceSize(lAction.getOrderSize());
			if(lOrder.getOrderType() ==1){
				mAskBook.reduce(lOrder, lAction.getOrderSize());
			}else{
				mBidBook.reduce(lOrder, lAction.getOrderSize());
			}
		}
		if(mCurrentSellValue.compareTo(mAskBook.getCurrentPrice())!=0){
			mCurrentSellValue = mAskBook.getCurrentPrice();
			mWriter.print(lAction.getActionTimeStamp()+ " B "+ (mCurrentSellValue.compareTo(mDefultValue)==0?"NA":mCurrentSellValue));
			mWriter.print(NEW_LINE);
		}
		if(mCurrentBuyValue.compareTo(mBidBook.getCurrentPrice())!=0){
			mCurrentBuyValue = mBidBook.getCurrentPrice();
			mWriter.print(lAction.getActionTimeStamp()+ " S "+ (mCurrentBuyValue.compareTo(mDefultValue)==0?"NA":mCurrentBuyValue) );
			mWriter.print(NEW_LINE);
		}
	}
	
	private class BidOrderComparator implements Comparator<Order>
	{
	    @Override
	    public int compare(Order x, Order y)
	    {
	        return x.getOrderPrice().compareTo(y.getOrderPrice());
	    }
	}
	
	private class AskOrderComparator implements Comparator<Order>
	{
	    @Override
	    public int compare(Order x, Order y)
	    {
	    	 return y.getOrderPrice().compareTo(x.getOrderPrice());
	    }
	}
}
