package com.smapl_android.ui.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.LoginStatusCallback;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.databinding.FragmentMiniLoginBinding;
import com.smapl_android.model.LoginInfoViewModel;
import com.smapl_android.ui.activities.MainActivity;
import com.smapl_android.ui.base.BaseFragment;

import java.util.Arrays;

public class LoginMiniFragment extends BaseFragment {


    public static final String TAG = LoginMiniFragment.class.getSimpleName();
    private Presenter presenter = new Presenter();
    private LoginInfoViewModel viewModel;

    private Button btnLogin;
    private CallbackManager callbackManager;
    private EditText editLogin;
    private ImageView imgLoginBg;
    private EditText editPassword;
    private ImageView imgPasswordBg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMiniLoginBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mini_login, container, false);
        viewModel = new LoginInfoViewModel(getActivity());
        binding.setUser(viewModel);
        binding.setPresenter(presenter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editLogin = (EditText) view.findViewById(R.id.edit_login);
        imgLoginBg = (ImageView) view.findViewById(R.id.img_edit_login_login_bg);
        imgLoginBg.setVisibility(View.INVISIBLE);

        editPassword = (EditText) view.findViewById(R.id.edit_password);
        imgPasswordBg = (ImageView) view.findViewById(R.id.img_edit_login_password_bg);
        imgPasswordBg.setVisibility(View.INVISIBLE);

        FacebookSdk.sdkInitialize(getCoreActivity().getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        btnLogin = (Button) view.findViewById(R.id.btn_login_facebook);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLogin();
            }
        });

        final EditText passwordEdit = (EditText) view.findViewById(R.id.edit_password);
        passwordEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    presenter.onLoginClicked(viewModel);
                    handled = true;
                }
                return handled;
            }
        });

        editLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    imgLoginBg.setVisibility(View.INVISIBLE);
                } else {
                    imgLoginBg.setVisibility(View.VISIBLE);
                }
            }
        });

        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    imgPasswordBg.setVisibility(View.INVISIBLE);
                } else {
                    imgPasswordBg.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean isAuthorized(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null){
            return false;
        } else {
            return true;
        }
    }

    private void facebookLogin() {
        if (isAuthorized()){
            getCoreActivity().replaceContent(new MainScreenFragment());
        } else {
            login();
        }

    }

    private void login(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","user_photos","public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getCoreActivity().replaceContent(new MainScreenFragment());
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "registerCallBack onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i(TAG, "registerCallBack onError " + error.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        AppEventsLogger.activateApp(getCoreActivity().getApplication());
    }

    @Override
    public void onStop() {
        super.onStop();

        AppEventsLogger.deactivateApp(getCoreActivity().getApplication());
    }

    public class Presenter {

        public void onLoginClicked(LoginInfoViewModel loginInfoViewModel) {
            final CoreRequest<Boolean> request = getCoreService()
                    .newRequest(getCoreActivity());
            request
                    .withLoading(R.string.wait_login)
                    .handleErrorAsDialog()
                    .handleSuccess(new SuccessOutput<Boolean>() {
                        @Override
                        public void onSuccess(Boolean result) {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
            getCoreService()
                    .login(loginInfoViewModel.phone.get(), loginInfoViewModel.password.get(), request);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivity result");
        if (resultCode == getActivity().RESULT_OK)
            callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
