package ca.ogsl.octopi.errorhandling;


/**
 * Class to map application related exceptions
 *
 * @author amacoder
 */
public class AppException extends Exception {

  private static final long serialVersionUID = -8999932578270387947L;

  /**
   * contains redundantly the HTTP status of the response sent back to the client in case of error,
   * so that the developer does not have to look into the response headers. If null a default
   */
  private Integer status;

  /**
   * application specific error code
   */
  private int code;

  /**
   * link documenting the exception
   */
  private String link;

  /**
   * @param status HTTP status
   * @param code Error code exposed inside json entity
   * @param message General message about the error
   * @param link Utility link for users witnessing the error
   */
  public AppException(int status, int code, String message, String link) {
    super(message);
    this.status = status;
    this.code = code;
    this.link = link;
  }

  public AppException() {
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

}
