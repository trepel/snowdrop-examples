package org.jboss.snowdrop.samples.sportsclub.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.math.BigDecimal;

import org.jboss.snowdrop.samples.sportsclub.payment.PaymentProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * @author Marius Bogoevici
 */
@WebService
public class PaymentNotificationService extends SpringBeanAutowiringSupport
{

   @Autowired
   private PaymentProcessor paymentProcessor;

   @WebMethod
   public Long notifyPayment(@WebParam(name="accountNumber") Long accountNumber, @WebParam(name="amount") BigDecimal amount)
   {
      return paymentProcessor.processPayment(accountNumber, amount);
   }


}
