package org.create.bankingApplication.account_service.model;

public class Constant {
	
	/*Imp=>	private constructor helps to not instantiate 
 	this “constant” enum class, as it contain constant 
 	final field which should not be change by anyone
	ACC_PREFIX is attached to every account number 
	{usually fix branch code is given to every bank 
	that should be prefixed to user’s account number}
*/
	private Constant() {
		
	}
	public static final String ACC_PREFIX = "060014";
}
