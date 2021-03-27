package kr.sjsoft.cameracontrolexample;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity {
    private static final int DEVICE_ADMIN_ADD_RESULT_ENABLE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.actionAdminActive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminActive();
            }
        });
        SwitchCompat actionCamera = findViewById(R.id.actionCamera);
        actionCamera.setVisibility(View.INVISIBLE);

    }

    /**
     * 관리자 권한 요청
     */
    private void adminActive() {
        ComponentName componentName = new ComponentName(this, DeviceAdministrator.class);
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        startActivityForResult(intent, DEVICE_ADMIN_ADD_RESULT_ENABLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 관리자 권한 요청 응답
        if (requestCode == DEVICE_ADMIN_ADD_RESULT_ENABLE) {
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName componentName = new ComponentName(this, DeviceAdministrator.class);

            // 권한 확인
            final boolean adminActive = devicePolicyManager.isAdminActive(componentName);

            // 카메라 사용 상태
            boolean cameraDisabled = devicePolicyManager.getCameraDisabled(componentName);

            // UI 초기화
            SwitchCompat actionCamera = findViewById(R.id.actionCamera);
            actionCamera.setVisibility(adminActive ? View.VISIBLE : View.INVISIBLE);
            actionCamera.setChecked(cameraDisabled);
            actionCamera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setCameraDisabled(isChecked);
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 카메라 비활성화
     *
     * @param cameraDisabled
     */
    private void setCameraDisabled(boolean cameraDisabled) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(this, DeviceAdministrator.class);
        devicePolicyManager.setCameraDisabled(componentName, cameraDisabled);
    }
}
