package com.nira.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nira.android.R;
import com.nira.android.adapter.CompanyAdapter;
import com.nira.android.db.RealmController;
import com.nira.android.utils.ActivityRouter;
import com.nira.android.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Suvesh on 21/08/2017 AD.
 */

public class SelectCompanyActivity extends AppCompatActivity {

    public static final String ID = "email";
    public static final String PHONE = "phone";

    @BindView(R.id.tvCName)
    TextInputLayout tvCName;
    @BindView(R.id.companyName)
    TextView tvCompanyName;
    @BindView(R.id.rlSelectC)
    RelativeLayout rlSelectComapany;
    @BindView(R.id.tvSelectedItem)
    TextView tvSelectedItem;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.score)
    EditText score;
    @BindView(R.id.tScore)
    TextInputLayout tScore;

    private Unbinder unbinder;
    private BottomSheetDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_company);
        unbinder = ButterKnife.bind(this);
        rlSelectComapany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpCompanyList();
            }
        });
        if (getIntent().getStringExtra(ID) != null) {
            email.setText(getIntent().getStringExtra(ID));

        }
        if(getIntent().getStringExtra(PHONE)!=null){
            email.setText(getIntent().getStringExtra(PHONE));
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString() == null && email.getText().toString().trim().length() == 0 && !Utils.isValidEmail(email.getText().toString()))
                    Toast.makeText(SelectCompanyActivity.this, "please enter valid email", Toast.LENGTH_SHORT).show();
                else if (name.getText().toString() == null && name.getText().toString().trim().length() == 0 && name.getText().toString().equalsIgnoreCase(""))
                    Toast.makeText(SelectCompanyActivity.this, "please enter your name", Toast.LENGTH_SHORT).show();
                else if (tvSelectedItem.getText().toString().equalsIgnoreCase("Select Company Name")) {
                    Toast.makeText(SelectCompanyActivity.this, "please select your Company", Toast.LENGTH_SHORT).show();
                } else{
                        if(tvCompanyName.getText().toString()!=null && tvCompanyName.getText().toString().trim().length()!=0) {
                            RealmController.addUserInDB(email.getText().toString(), name.getText().toString(), tvCompanyName.getText().toString(), Integer.valueOf(score.getText().toString()), "","");
                            ActivityRouter.navigateToProfileActivity(SelectCompanyActivity.this, null, email.getText().toString(),null);
                        }else if(score.getText().toString()!=null && score.getText().toString().trim().length()!=0 ){
                            RealmController.addUserInDB(email.getText().toString(), name.getText().toString(), tvSelectedItem.getText().toString(), Integer.valueOf(score.getText().toString()), "",",");
                            ActivityRouter.navigateToProfileActivity(SelectCompanyActivity.this, null, email.getText().toString(),null);
                        }else {
                            RealmController.addUserInDB(email.getText().toString(), name.getText().toString(), tvSelectedItem.getText().toString(), 10, "","");
                            ActivityRouter.navigateToProfileActivity(SelectCompanyActivity.this, null, email.getText().toString(),null);
                        }
                    }
            }
        });

    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }


    private boolean dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            return true;
        }

        return false;
    }

    private void setUpCompanyList() {
        if (dismissDialog()) {
            return;
        }
        dialog = new BottomSheetDialog(SelectCompanyActivity.this);
        View view = LayoutInflater.from(SelectCompanyActivity.this).inflate(R.layout.layout_spinner, null);
        RecyclerView rvCompany = (RecyclerView) view.findViewById(R.id.rvCompany);
        LinearLayoutManager verticalRecycler = new LinearLayoutManager(SelectCompanyActivity.this, LinearLayoutManager.VERTICAL, false);
        verticalRecycler.setOrientation(LinearLayoutManager.VERTICAL);
        rvCompany.setLayoutManager(verticalRecycler);
        CompanyAdapter adapter = new CompanyAdapter(SelectCompanyActivity.this, makeCompanyList());
        adapter.setCallbacks(new CompanyAdapter.Callbacks() {
            @Override
            public void onSelectCompany(String company) {
                Log.d("Selected Company", company);
                if (company.equalsIgnoreCase("None of the above")) {
                    tvCName.setVisibility(View.VISIBLE);
                    tScore.setVisibility(View.VISIBLE);
                    tvSelectedItem.setText(tvCompanyName.getText().toString());
                } else {
                    tvCName.setVisibility(View.GONE);
                    tScore.setVisibility(View.GONE);
                    tvSelectedItem.setText(company);
                }
                dialog.dismiss();
            }
        });
        rvCompany.setAdapter(adapter);
        dialog.setContentView(view);
        dialog.show();
    }

    private List<String> makeCompanyList() {
        List<String> list = new ArrayList<>();
        list.add("Infosys");
        list.add("TCS");
        list.add("Tech Mahindra");
        list.add("Flipkart");
        list.add("DirectI");
        list.add("None of the above");
        return list;
    }

}
