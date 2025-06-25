package com.newgen.cbg.utils;


public class NameMatch {
	public static void main(String[] args){
		
		String a = "123";
		String s2 = "321";
		String s3 = a+s2;
		System.out.println(s3);
		
		String passport = "Amt PRADHaN abcd kUMAR sc".toUpperCase().trim().replaceAll(" +", " ");
		String Manual = "Amit PRADHaN abcd kUMAR stc".toUpperCase().trim().replaceAll(" +", " ");
		
		
		if(passport.charAt(0) != Manual.charAt(0)){
			System.out.print("False");
			return;
		} else if (passport.charAt(passport.length()-1) != Manual.charAt(Manual.length()-1)){
			System.out.print("False");
			return;

		} else {
			int n = passport.length(), m = Manual.length();
		    int i = 0, j = 0;
		    while (i < n && j < m) {
		        if (passport.charAt(i) == Manual.charAt(j)){
		        	System.out.println(passport.charAt(i));
		        	System.out.println(Manual.charAt(j));
		            i++;
		        }
		        j++;
		    }
		    System.out.print(i == n);
		}
			
		
	     
		
	}

}
