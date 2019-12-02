package ca.ogsl.octopi.errorhandling;

import java.lang.reflect.InvocationTargetException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@XmlRootElement
public class ErrorMessage {

  private static final Logger logger = LogManager.getLogger();

  /**
   * contains the same HTTP Status code returned by the server
   */
  @XmlElement(name = "status")
  private Integer status;

  /**
   * message describing the error
   */
  @XmlElement(name = "message")
  private String message;

  /**
   * link point to page where the error message is documented
   */
  @XmlElement(name = "link")
  private String link;


  ErrorMessage(AppException ex) {
    try {
      BeanUtils.copyProperties(this, ex);
    } catch (IllegalAccessException | InvocationTargetException e) {
      // TODO Auto-generated catch block
      logger.error("Cannot copy properties", e);
    }
  }

  public ErrorMessage(NotFoundException ex) {
    this.status = Response.Status.NOT_FOUND.getStatusCode();
    this.message = ex.getMessage();
    this.link = "https://jersey.java.net/apidocs/2.8/jersey/javax/ws/rs/NotFoundException.html";
  }

  ErrorMessage() {
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }
}
