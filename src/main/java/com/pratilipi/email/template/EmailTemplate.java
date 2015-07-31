package com.pratilipi.email.template;

public class EmailTemplate {
	
	private String senderName;
	
	private String senderEmail;
	
	private String userPassword;
	
	private String recipientName;
	
	private String recipientEmail;
	
	private String templateName;
	
	private String language;
	
	private String subject;
	
	
	public EmailTemplate() {}
	
	public EmailTemplate( String type ) {
		if( type.toLowerCase().equals( "welcome" )) {
			this.senderName = "Ranjeet (Pratilipi)";
			this.senderEmail = "contact@pratilipi.com";
			this.templateName = "com/pratilipi/email/template/welcome";
			this.subject = "Account Activation: Pratilipi.com";
		}
		else if( type.toLowerCase().equals( "passwordreset" )) {
			this.senderName = "Pratilipi";
			this.senderEmail = "contact@pratilipi.com";
			this.templateName = "com/pratilipi/email/template/passwordreset";
			this.subject = "Forgot Password: Pratilipi.com";
		}
	}
	
	
	public String getSenderName() {
		return this.senderName;
	}

	public void setSenderName( String senderName ) {
		this.senderName = senderName;
	}

	public String getSenderEmail() {
		return this.senderEmail;
	}

	public void setSenderEmail( String senderEmail ) {
		this.senderEmail = senderEmail;
	}
	
	public String getPassword() {
		return this.userPassword;
	}
	
	public String getRecipientName() {
		return this.recipientName;
	}

	public void setRecipientName( String recipientName ) {
		this.recipientName = recipientName;
	}

	public String getRecipientEmail() {
		return this.recipientEmail;
	}

	public void setRecipientEmail( String recipientEmail ) {
		this.recipientEmail = recipientEmail;
	}
	
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage( String language ) {
		this.language = language;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject( String subject ) {
		this.subject = subject;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName( String templateName ) {
		this.templateName = templateName;
	}
	
}