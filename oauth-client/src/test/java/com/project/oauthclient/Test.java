package com.project.oauthclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Test {
	

	public class NPVModel{
		private Integer period;
		private Integer amountOld;
		private Integer amountNew;
		public Integer getPeriodOld() {
			return period;
		}
		public void setPeriodOld(Integer periodOld) {
			this.period = periodOld;
		}
		public Integer getAmountOld() {
			return amountOld;
		}
		public void setAmountOld(Integer amountOld) {
			this.amountOld = amountOld;
		}
		
		public Integer getAmountNew() {
			return amountNew;
		}
		public void setAmountNew(Integer amountNew) {
			this.amountNew = amountNew;
		}
		public NPVModel(Integer periodOld, Integer amountOld, Integer amountNew) {
			super();
			this.period = periodOld;
			this.amountOld = amountOld;
			this.amountNew = amountNew;
		}
		@Override
		public String toString() {
			return "\n NPVModel [period=" + period + ", amountOld=" + amountOld + ", amountNew=" + amountNew + "]";
		}
		
		
		

		
	}
	
	
	
	public NPVModel getModel(Integer periodOld, Integer amountOld, Integer amountNew) {
		return new NPVModel(periodOld, amountOld, amountNew);
	}



	public static void main(String args[]) {
		
		List<NPVModel> oldList = new ArrayList<>();
		List<NPVModel> newList = new ArrayList<>();

		
		Test test = new Test();
		oldList.add(test.getModel(2009, 1234,null));
		oldList.add(test.getModel(2010, 1234,null));
		oldList.add(test.getModel(2011, 1234,null));
		oldList.add(test.getModel(2012, 1234,null));
		
		newList.add(test.getModel(2009,null, 1111));
		newList.add(test.getModel(2008,null, 1111));
		newList.add(test.getModel(2012,null, 1234));
		
		Set<Integer> periodList =  oldList.stream().map(item -> item.getPeriodOld()).collect(Collectors.toSet());
	//	periodList.addAll(newList.stream().map(item -> item.getPeriodOld()).collect(Collectors.toSet()));
		
		System.out.println(periodList);
		
		
		
		oldList.forEach( oldItem ->{
			
			newList.forEach( newItem -> {
				if(newItem.getPeriodOld().equals(oldItem.getPeriodOld())) {
					oldItem.setAmountNew(newItem.getAmountNew());
				}
			});
		});
		
		oldList.addAll(newList.stream().filter( newItem -> !periodList.contains(newItem.getPeriodOld())).collect(Collectors.toList()));
		
		
		System.out.println(oldList.toString());


		
		
	}
}

