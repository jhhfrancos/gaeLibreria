package co.edu.unal.client.paneles;

import co.edu.unal.client.services.LoginGmailService;
import co.edu.unal.client.services.LoginGmailServiceAsync;
import co.edu.unal.shared.LoginInfo;

import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.api.gwt.oauth2.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;

public class LoginGmail extends Composite {
	
	
		private static final Auth AUTH = Auth.get();
		private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
		private static final String GOOGLE_CLIENT_ID = "593615925078-sf02p38k0fp3e99qtklk1bkai3gctbsr.apps.googleusercontent.com";
		private static final String PLUS_ME_SCOPE = "https://www.googleapis.com/auth/userinfo.profile";

		private final HorizontalPanel loginPanel = new HorizontalPanel();
		private final Anchor signInLink = new Anchor("");
		private final Image loginImage = new Image();
		private final TextBox nameField = new TextBox();
		
		private MenuPrincipal menuPrincipal;

		private final LoginGmailServiceAsync loginGmailService = GWT.create(LoginGmailService.class);


		private void loadLogin(final LoginInfo loginInfo) {
			signInLink.setHref(loginInfo.getLoginUrl());
			signInLink.setText("Please, sign in with your Google Account");
			signInLink.setTitle("Sign in");
		}

		private void loadLogout(final LoginInfo loginInfo) {
			signInLink.setHref(loginInfo.getLogoutUrl());
			signInLink.setText("Sign out to " + loginInfo.getName());
			signInLink.setTitle("Sign out");
		}

		private void addGoogleAuthHelper() {
			final AuthRequest req = new AuthRequest(GOOGLE_AUTH_URL, GOOGLE_CLIENT_ID)
					.withScopes(PLUS_ME_SCOPE);
			AUTH.login(req, new Callback<String, Throwable>() {
				@Override
				public void onSuccess(final String token) {

					if (!token.isEmpty()) {
						loginGmailService.loginDetails(token, new AsyncCallback<LoginInfo>() {
							@Override
							public void onFailure(final Throwable caught) {
								GWT.log("loginDetails -> onFailure");
							}

							@Override
							public void onSuccess(final LoginInfo loginInfo) {
								signInLink.setText(loginInfo.getName());
								nameField.setText(loginInfo.getName());
								signInLink.setStyleName("login-area");
								loginImage.setUrl(loginInfo.getPictureUrl());
								loginImage.setVisible(false);
								loginPanel.add(loginImage);
								loginImage.addLoadHandler(new LoadHandler() {
									@Override
									public void onLoad(final LoadEvent event) {
										final int newWidth = 24;
										final com.google.gwt.dom.client.Element element = event
												.getRelativeElement();
										if (element.equals(loginImage.getElement())) {
											final int originalHeight = loginImage.getOffsetHeight();
											final int originalWidth = loginImage.getOffsetWidth();
											if (originalHeight > originalWidth) {
												loginImage.setHeight(newWidth + "px");
											} else {
												loginImage.setWidth(newWidth + "px");
											}
											loginImage.setVisible(true);
										}
									}
								});
							}
						});
					}
				}

				@Override
				public void onFailure(final Throwable caught) {
					GWT.log("Error -> loginDetails\n" + caught.getMessage());
				}
			});
		}

		

		public LoginGmail(MenuPrincipal menuPrincipalIn){
			this.menuPrincipal = menuPrincipalIn;
			initWidget(loginPanel);
			final Button sendButton = new Button("Send");
			
			sendButton.setEnabled(false);		
			nameField.setEnabled(false);	

			signInLink.setName("login-area");
			signInLink.setTitle("sign out");
			//loginImage.setClassName("login-area");
			loginPanel.add(signInLink);
			
			final StringBuilder userEmail = new StringBuilder();
			loginGmailService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
				@Override
				public void onFailure(final Throwable caught) {
					GWT.log("login -> onFailure");
				}

				@Override
				public void onSuccess(final LoginInfo result) {
					if (result.getName() != null && !result.getName().isEmpty()) {
						addGoogleAuthHelper();
						loadLogout(result);
						sendButton.setEnabled(true);	
						nameField.setEnabled(true);
						menuPrincipal.activarTabs();
						
						
					} else {
						loadLogin(result);
						menuPrincipal.desactivarTabs();
					}
					userEmail.append(result.getEmailAddress());
				}
			});
		}
}
