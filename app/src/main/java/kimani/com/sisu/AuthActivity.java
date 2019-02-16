package kimani.com.sisu;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import kimani.com.sisu.utils.NetworkResponse;
import kimani.com.sisu.views.AuthView;

public class AuthActivity extends AppCompatActivity {

    @BindView(R.id.login_btn) AppCompatButton login_btn;
    @BindView(R.id.email_address) AppCompatEditText email_address;
    @BindView(R.id.password) AppCompatEditText password;
    @BindView(R.id.pr) ProgressBar pr;

    private AuthView authView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        authView = ViewModelProviders.of(this).get(AuthView.class);

        if(authView.getToken()!=null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        authView.monitor.observe(this, new Observer<NetworkResponse>() {
            @Override
            public void onChanged(@Nullable NetworkResponse networkResponse) {
                if(networkResponse==null)
                    return;
                if(networkResponse.is_loading){
                    pr.setVisibility(View.VISIBLE);
                }else{
                    pr.setVisibility(View.GONE);
                }
                if(networkResponse.message!=null && !networkResponse.message.equals(""))
                    Snackbar.make(pr, networkResponse.message , Snackbar.LENGTH_LONG).show();

                if(networkResponse.response_code == 200){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authView.attemptAuth(email_address.getText().toString(),password.getText().toString());
            }
        });

    }
}
