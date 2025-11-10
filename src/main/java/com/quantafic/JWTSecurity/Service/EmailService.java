package com.quantafic.JWTSecurity.Service;

import java.math.BigDecimal;
import java.util.Properties;

import com.quantafic.JWTSecurity.Model.LoanOffers;
import com.quantafic.JWTSecurity.Model.Notification;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import com.quantafic.JWTSecurity.Model.Application;
import com.quantafic.JWTSecurity.Model.Customers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${smtp.username}")
    private String username;

    @Value("${smtp.password}")
    private String password;

    @Value("${smtp.host}")
    private String smtpHost;

    @Value("${smtp.port}")
    private String smtpPort;

    public Notification sendEmail(String toEmail, String subject, String body) {
        Notification notification = new Notification();
        notification.setToEmail(toEmail);
        notification.setSubject(subject);
        notification.setBody(body);
        return sendEmail(notification);
    }

    private Notification sendEmail(Notification notification) {
        try {
            notification.setFromEmail(username);

            Properties props = new Properties();
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", smtpPort);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(notification.getToEmail()));
            msg.setSubject(notification.getSubject());
            msg.setContent(notification.getBody(), "text/html; charset=utf-8");

            Transport.send(msg);

            notification.setStatus("C");
            notification.setMessage("Success: " + notification.getSubject());
        } catch (Exception e) {
            e.printStackTrace();
            notification.setStatus("E");
            notification.setMessage("Error: " + e.getMessage());
        }
        return notification;
    }

    public void sendApplicationCreatedEmail(Customers customer, Application application) {
        String subject = "Your Application has been Created - Application ID: " + application.getApplicationId();
        String body = "Hello " + customer.getFirstName() + ",<br><br>"
                + "We’re glad to inform you that your application has been successfully created. 🎉<br><br>"
                + "<b>Application Details:</b><br>"
                + "- Application ID: " + application.getApplicationId() + "<br><br>"
                + "Please keep this ID safe for future reference.<br><br>"
                + "Best regards,<br>Your Company Name";

        sendEmail(customer.getEmail(), subject, body);
    }

    public void sendDocumentVerifiedEmail(Customers customer, Application application) {
        String subject = "Your Documents Have Been Verified - Application ID: " + application.getApplicationId();

        String body = "Hello " + customer.getFirstName() + ",<br><br>"
                + "Good news! ✅<br>"
                + "Your submitted documents for the loan application have been successfully <b>verified</b>.<br><br>"
                + "<b>Application Details:</b><br>"
                + "- Application ID: " + application.getApplicationId() + "<br><br>"
                + "Our team will now proceed with the next steps of credit assessment and loan processing.<br><br>"
                + "You can track your application status anytime through your dashboard.<br><br>"
                + "Best regards,<br>"
                + "<b>Your Company Name</b>";

        sendEmail(customer.getEmail(), subject, body);
    }

    public void sendLoanOfferEmail(Customers customer, Application application) {
        String subject = "Your Loan Offer is Ready! - Application ID: " + application.getApplicationId();

        String body = "Hello " + customer.getFirstName() + ",<br><br>"
                + "We’re excited to inform you that based on your application, we have generated a <b>loan offer</b> for you. 🎉<br><br>"
                + "<b>Application Details:</b><br>"
                + "- Application ID: " + application.getApplicationId() + "<br><br>"
                + "Please visit our website to review the offer details and <b>accept or decline</b> it at your convenience.<br><br>"
                + "👉 <a href='https://yourcompanywebsite.com/login' target='_blank'>Click here to view your loan offer</a><br><br>"
                + "Make sure to respond soon to avoid offer expiration.<br><br>"
                + "Best regards,<br>"
                + "<b>Your Company Name</b>";

        sendEmail(customer.getEmail(), subject, body);
    }

    public void sendAgreementSignedEmail(Customers customer, Application application) {
        String subject = "Agreement Signed Successfully - Application ID: " + application.getApplicationId();

        String body = "Hello " + customer.getFirstName() + ",<br><br>"
                + "Thank you for completing the final step! ✍️<br>"
                + "We’re pleased to inform you that you have successfully <b>signed the loan agreement</b> and <b>accepted the terms and conditions</b> for your application.<br><br>"
                + "<b>Application Details:</b><br>"
                + "- Application ID: " + application.getApplicationId() + "<br><br>"
                + "Our team will now initiate the final review and disbursement process.<br><br>"
                + "You can track the progress of your loan anytime through your dashboard.<br><br>"
                + "Best regards,<br>"
                + "<b>Your Company Name</b>";

        sendEmail(customer.getEmail(), subject, body);
    }

    public void sendLoanDisbursedEmail(Customers customer, Application application , BigDecimal ammount) {
        String subject = "Loan Amount Disbursed - Application ID: " + application.getApplicationId();

        String body = "Hello " + customer.getFirstName() + ",<br><br>"
                + "Great news! 💰<br>"
                + "We are pleased to inform you that your loan amount has been <b>successfully disbursed</b>.<br><br>"
                + "<b>Application Details:</b><br>"
                + "- Application ID: " + application.getApplicationId() + "<br>"
                + "- Disbursed Amount: ₹" + ammount + "<br><br>"
                + "The amount should reflect in your bank account shortly. You can track the status anytime through your dashboard.<br><br>"
                + "If you have any questions, feel free to reach out to our support team.<br><br>"
                + "Best regards,<br>"
                + "<b>Your Company Name</b>";

        sendEmail(customer.getEmail(), subject, body);
    }

    public void sendLoanRejectionEmail(Customers customer, Application application, String rejectionReason) {
        String subject = "Loan Application Rejected - Application ID: " + application.getApplicationId();

        String body = "Hello " + customer.getFirstName() + ",<br><br>"
                + "We regret to inform you that your loan application has been <b>rejected</b>. ❌<br><br>"
                + "<b>Application Details:</b><br>"
                + "- Application ID: " + application.getApplicationId() + "<br>"
                + "Reason for rejection: <b>" + rejectionReason + "</b><br><br>"
                + "You may contact our support team for further assistance or to explore other loan options.<br><br>"
                + "Best regards,<br>"
                + "<b>Your Company Name</b>";

        sendEmail(customer.getEmail(), subject, body);
    }

    public void sendLoanOfferAcceptedEmail(Customers customer, Application application , LoanOffers loanOffers) {
        String subject = "Loan Offer Accepted - Application ID: " + application.getApplicationId();

        String body = "Hello " + customer.getFirstName() + ",<br><br>"
                + "Thank you for your confirmation! 🎉<br>"
                + "We are glad to inform you that you have <b>accepted the loan offer</b> for your application.<br><br>"
                + "<b>Application Details:</b><br>"
                + "- Application ID: " + application.getApplicationId() + "<br>"
                + "- APPROVED Amount: ₹" + loanOffers.getOfferAmount() + "<br>"
                + "- Tenure: " + loanOffers.getTenure() + " months<br>"
                + "- Interest Rate: " + loanOffers.getRoi() + "%<br><br>"
                + "Our team will now proceed with the agreement and disbursement steps.<br><br>"
                + "Best regards,<br>"
                + "<b>Your Company Name</b>";

        sendEmail(customer.getEmail(), subject, body);
    }

    public void sendLoanOfferRejectedEmail(Customers customer, Application application , LoanOffers loanOffers) {
        String subject = "Loan Offer Rejected - Application ID: " + application.getApplicationId();

        String body = "Hello " + customer.getFirstName() + ",<br><br>"
                + "We have received your response regarding the loan offer. ⚠️<br>"
                + "You have <b>rejected the loan offer</b> associated with your application.<br><br>"
                + "<b>Application Details:</b><br>"
                + "- Application ID: " + application.getApplicationId() + "<br>"
                + "- Offered Amount: ₹" + loanOffers.getOfferAmount() + "<br>"
                + "- Interest Rate: " + loanOffers.getRoi() + "%<br><br>"
                + "If this was done by mistake or you wish to reconsider, please contact our support team for assistance.<br><br>"
                + "Best regards,<br>"
                + "<b>Your Company Name</b>";

        sendEmail(customer.getEmail(), subject, body);
    }


}
