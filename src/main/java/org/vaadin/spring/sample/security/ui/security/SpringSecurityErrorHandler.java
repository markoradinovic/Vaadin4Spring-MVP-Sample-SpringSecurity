package org.vaadin.spring.sample.security.ui.security;

import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.access.AccessDeniedException;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;

import com.vaadin.event.ListenerMethod.MethodException;
import com.vaadin.server.AbstractErrorMessage;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.ClientConnector.ConnectorErrorEvent;
import com.vaadin.server.ServerRpcManager.RpcInvocationException;
import com.vaadin.shared.Connector;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;

/**
 * Re-write of {@link DefaultErrorHandler} that handles Spring Security AccessDeniedException
 * 
 * @author marko
 *
 */
@SuppressWarnings("serial")
public class SpringSecurityErrorHandler implements ErrorHandler {
	
	@Override
    public void error(ErrorEvent event) {
        doDefault(event);
    }

    public static void doDefault(ErrorEvent event) {
        Throwable t = event.getThrowable();
        if (t instanceof SocketException) {
            // Most likely client browser closed socket
            getLogger().info(
                    "SocketException in CommunicationManager."
                            + " Most likely client (browser) closed socket.");
            return;
        }

        t = findRelevantThrowable(t);
        
        /*
         * Handle SpringSecurity 
         */
        if (t instanceof AccessDeniedException) {
        	
        	EventBus eventBus = SpringApplicationContext.getEventBus();
        	eventBus.publish(EventScope.UI, eventBus, new AccessDeniedEvent(t));
        	
        	getLogger().log(Level.FINE, "Access is denied", t);
        	return;
        }

        // Finds the original source of the error/exception
        AbstractComponent component = findAbstractComponent(event);
        if (component != null) {
            // Shows the error in AbstractComponent
            ErrorMessage errorMessage = AbstractErrorMessage
                    .getErrorMessageForException(t);
            component.setComponentError(errorMessage);
        }

        // also print the error on console
        getLogger().log(Level.SEVERE, "", t);
    }

    /**
     * Vaadin wraps exceptions in its own and due to reflection usage there
     * might be also other irrelevant exceptions that make no sense for Vaadin
     * users (~developers using Vaadin). This method tries to choose the
     * relevant one to be reported.
     * 
     * @since 7.2
     * @param t
     *            throwable given for default error handler
     * @return the throwable that is relevant for Vaadin users
     */
    private static Throwable findRelevantThrowable(Throwable t) {
        try {
            if ((t instanceof RpcInvocationException)
                    && (t.getCause() instanceof InvocationTargetException)) {
                /*
                 * RpcInvocationException (that always wraps irrelevant
                 * java.lang.reflect.InvocationTargetException) might only be
                 * relevant for core Vaadin developers.
                 */
                return findRelevantThrowable(t.getCause().getCause());
            } else if (t instanceof MethodException) {
                /*
                 * Method exception might only be relevant for core Vaadin
                 * developers.
                 */
                return t.getCause();
            }
        } catch (Exception e) {
            // NOP, just return the original one
        }
        return t;
    }

    private static Logger getLogger() {
        return Logger.getLogger(SpringSecurityErrorHandler.class.getName());
    }

    /**
     * Returns the AbstractComponent associated with the given error if such can
     * be found
     * 
     * @param event
     *            The error to investigate
     * @return The {@link AbstractComponent} to error relates to or null if
     *         could not be determined or if the error does not relate to any
     *         AbstractComponent.
     */
    public static AbstractComponent findAbstractComponent(
            com.vaadin.server.ErrorEvent event) {
        if (event instanceof ConnectorErrorEvent) {
            Component c = findComponent(((ConnectorErrorEvent) event)
                    .getConnector());
            if (c instanceof AbstractComponent) {
                return (AbstractComponent) c;
            }
        }

        return null;
    }

    /**
     * Finds the nearest component by traversing upwards in the hierarchy. If
     * connector is a Component, that Component is returned. Otherwise, looks
     * upwards in the hierarchy until it finds a {@link Component}.
     * 
     * @return A Component or null if no component was found
     */
    public static Component findComponent(Connector connector) {
        if (connector instanceof Component) {
            return (Component) connector;
        }
        if (connector.getParent() != null) {
            return findComponent(connector.getParent());
        }

        return null;
    }


}
