package com.splwg.cm.domain.messagesender;

import com.splwg.base.support.ExtendableInstanceFactory;
import com.splwg.base.support.context.FrameworkSession;
import com.splwg.base.support.context.SessionHolder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class NetworkReporterUtil
{
	public static interface NetworkCallInfo
	{
		void incrementBytesSent(long param1Long);

		void incrementBytesReceived(long param1Long);

		long getNumBytesReceived();

		long getNumBytesSent();
	}

	public static class NetworkCallInfoImpl
			implements NetworkCallInfo
	{
		private long bytesSent;
		private long bytesReceived;

		public void incrementBytesSent(long num) {
			this.bytesSent += num;
		}

		public void incrementBytesReceived(long num) {
			this.bytesReceived += num;
		}

		public long getNumBytesReceived() {
			return this.bytesReceived;
		}

		public long getNumBytesSent() {
			return this.bytesSent;
		}
	}

	public static interface NetworkCallable<T>
	{
		T execute(NetworkReporterUtil.NetworkCallInfo param1NetworkCallInfo) throws Exception;
	}

	public static class NetworkReporter
	{
		public <T> T execute(String component, String url, NetworkReporterUtil.NetworkCallable<T> callable) throws Exception {
			NetworkReporterUtil.NetworkCallInfo info = new NetworkReporterUtil.NetworkCallInfoImpl();
			return (T) callable.execute(info);
		}

		public <T> T execute(String component, String url, String safeToLogPathInfo, NetworkReporterUtil.NetworkCallable<T> callable) throws Exception {
			NetworkReporterUtil.NetworkCallInfo info = new NetworkReporterUtil.NetworkCallInfoImpl();
			return (T) callable.execute(info);
		}

		public OutputStream wrapOutputStream(String component, String url, OutputStream stream) {
			return new BufferedOutputStream(stream);
		}

		public InputStream wrapInputStream(String component, String url, InputStream stream) {
			return new BufferedInputStream(stream);
		}

		public InputStream wrapInputStream(String component, String url, String safeToLogPathInfo, InputStream stream) {
			return new BufferedInputStream(stream);
		}

		public OutputStream wrapOutputStream(String component, String url, String safeToLogPathInfo, OutputStream stream) {
			return new BufferedOutputStream(stream);
		}
	}

	private static final NetworkReporter reporterCreator = (NetworkReporter) (new ExtendableInstanceFactory("com.oracle.ouaf.networkReportingWrapperClass", NetworkReporter.class, () ->
			new NetworkReporter()))
			.getInstance();

	public static <T> T executeNetworkCall(String component, String url, NetworkCallable<T> callable) throws Exception {
		FrameworkSession session = (FrameworkSession) SessionHolder.getSession();
		if (session != null) {
			session.assertDeadlineNotExceeded();
		}
		T result = (T) reporterCreator.execute(component, url, callable);
		if (session != null) {
			session.assertDeadlineNotExceeded();
		}
		return result;
	}

	public static OutputStream wrapOutputStream(String component, String url, OutputStream out) {
		return reporterCreator.wrapOutputStream(component, url, out);
	}

	public static InputStream wrapInputStream(String component, String url, InputStream in) {
		return reporterCreator.wrapInputStream(component, url, in);
	}

	public static <T> T executeNetworkCall(String component, String url, String safeToLogPathInfo, NetworkCallable<T> callable) throws Exception {
		FrameworkSession session = (FrameworkSession) SessionHolder.getSession();
		if (session != null) {
			session.assertDeadlineNotExceeded();
		}
		T result = (T) reporterCreator.execute(component, url, safeToLogPathInfo, callable);
		if (session != null) {
			session.assertDeadlineNotExceeded();
		}
		return result;
	}

	public static InputStream wrapInputStream(String component, String url, String safeToLogPathInfo, InputStream in) {
		return reporterCreator.wrapInputStream(component, url, safeToLogPathInfo, in);
	}

	public static OutputStream wrapOutputStream(String component, String url, String safeToLogPathInfo, OutputStream out) {
		return reporterCreator.wrapOutputStream(component, url, safeToLogPathInfo, out);
	}
}
