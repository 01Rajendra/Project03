package in.co.rays.project_3.exception;

import java.util.List;

/**
 * @author Rajendra Negi
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;
	private List list;

	public ApplicationException(String msg) {
		super(msg);
	}

	public ApplicationException(Throwable cause, List list) {
		super(cause);
		this.list = list;
	}

	public List getDemoList() {
		return list;
	}
}
