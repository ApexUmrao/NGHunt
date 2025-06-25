package com.newgen.cbg.utils;

import java.util.Calendar;
import java.util.Date;

public class CopyOfLoanInstallmentStartDate {

	public static void main(String[] arg)
	{
		try{

			String salaryD = "19";

			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			Date disburmentdate = c.getTime();

			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), Integer.parseInt(salaryD));
			Date salaryDate =  c.getTime();
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(salaryDate);

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(disburmentdate);

			if(salaryDate.compareTo(disburmentdate)==1 ){
				cal.add(Calendar.DAY_OF_MONTH, 30);
			}
			else{
				cal.add(Calendar.DAY_OF_MONTH, 60);
			}
			int disburmentDay = cal.get(Calendar.DAY_OF_MONTH);
			int X = disburmentDay;
			int finaldate = 0;
			int finalMonth = cal.get(Calendar.MONTH);
			int finalYear = cal.get(Calendar.YEAR);
			if((X >= 1 && X < 5)){
				finaldate=5;
			}
			if((X >= 25 && X <= 31)){
				finaldate=5;
				finalMonth = finalMonth+1;
				if(finalMonth==12){
					finalMonth = 0;
					finalYear = finalYear+1;
				}
			}
			if((X >= 5 && X < 10)){
				finaldate=10;
			}
			if((X >= 10 && X < 15)){
				finaldate=15;
			}
			else if((X >= 15 && X < 25)){

				finaldate=25;
			}
			Calendar cal2 = Calendar.getInstance();
			cal2.set(finalYear,finalMonth,finaldate);
//			System.out.println(cal2.getTime());

		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
