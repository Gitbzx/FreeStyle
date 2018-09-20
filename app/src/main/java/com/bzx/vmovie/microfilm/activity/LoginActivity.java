package com.bzx.vmovie.microfilm.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bzx.vmovie.microfilm.R;
import com.bzx.vmovie.microfilm.base.BaseActivity;
import com.bzx.vmovie.microfilm.constants.Constants;
import com.bzx.vmovie.microfilm.utils.CodeUtils;
import com.bzx.vmovie.microfilm.utils.KeyboardUtil;
import com.bzx.vmovie.microfilm.utils.MyToast;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements View.OnTouchListener{

    @BindView(R.id.phone_line)
    View phoneLine;
    @BindView(R.id.rl_user_number)
    RelativeLayout rlUserNumber;
    @BindView(R.id.phone_name)
    TextView phoneName;
    @BindView(R.id.input_delete)
    ImageButton inputDelete;
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    @BindView(R.id.rl_login)
    RelativeLayout rlLogin;
    @BindView(R.id.key_line)
    View keyLine;
    @BindView(R.id.key_bg)
    RelativeLayout keyBg;
    @BindView(R.id.key_name)
    TextView keyName;
    @BindView(R.id.img_key)
    ImageView imgKey;
    @BindView(R.id.lin_keyimg)
    LinearLayout linKeyimg;
    @BindView(R.id.img_key_delete)
    ImageView imgKeyDelete;
    @BindView(R.id.key)
    EditText key;
    @BindView(R.id.key_input)
    RelativeLayout keyInput;
    @BindView(R.id.piccheck_line)
    View piccheckLine;
    @BindView(R.id.piccheck_bg)
    RelativeLayout piccheckBg;
    @BindView(R.id.imageview_piccheck)
    ImageView imageviewPiccheck;
    @BindView(R.id.piccheck_number)
    EditText piccheckNumber;
    @BindView(R.id.re_piccheck)
    RelativeLayout rePiccheck;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.iv_login_from_qq)
    ImageView ivLoginFromQq;
    @BindView(R.id.iv_login_from_sina)
    ImageView ivLoginFromSina;
    @BindView(R.id.iv_login_from_wechat)
    ImageView ivLoginFromWechat;
    @BindView(R.id.fl_login_root_view)
    FrameLayout flLoginRootView;

    private boolean showKey;//是否显示明文
    private KeyboardUtil keyboardUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    InputFilter filter=new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if(source.equals(" ")){
                return "";
            }else{
                return null;
            }
        }
    };

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        addLayoutListener(flLoginRootView, tvLogin);

        phoneNumber.setOnTouchListener(this);
        phoneNumber.addTextChangedListener(mPhoneNumWatcher);
        phoneNumber.setOnFocusChangeListener(mFocusListenerChangeForphoneNumber);
        phoneNumber.setFilters(new InputFilter[]{filter});

        key.setOnTouchListener(this);
        key.setOnFocusChangeListener(mFocusChangeForKey);
        key.addTextChangedListener(mKeyWatcher);
        key.setFilters(new InputFilter[]{filter});

        piccheckNumber.setOnTouchListener(this);
        piccheckNumber.addTextChangedListener(checkWatcher);
        imageviewPiccheck.setImageBitmap(CodeUtils.getInstance().createBitmap());

        Method setShowSoftInputOnFocus = null;
        try {
            setShowSoftInputOnFocus = key.getClass().getMethod(
                    "setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(key, false);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

        key.clearFocus();
        phoneNumber.requestFocus();
    }

    @Override
    protected void setupView() {

    }

    /**
     * 1、获取main在窗体的可视区域
     * 2、获取main在窗体的不可视区域高度
     * 3、判断不可视区域高度
     * 1、大于100：键盘显示  获取Scroll的窗体坐标
     * 算出main需要滚动的高度，使scroll显示。
     * 2、小于100：键盘隐藏
     *
     * @param main   根布局
     * @param scroll 需要显示的最下方View
     */
    public void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                main.getWindowVisibleDisplayFrame(rect);
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                if (mainInvisibleHeight > 100) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                    if (srollHeight > 0) {
                        main.scrollTo(0, srollHeight);
                    }
                } else {
                    main.scrollTo(0, 0);
                }
            }
        });
    }

    @OnClick({R.id.input_delete, R.id.phone_number, R.id.img_key_delete, R.id.key, R.id.imageview_piccheck,
            R.id.piccheck_number, R.id.tv_login, R.id.iv_login_from_qq, R.id.iv_login_from_sina,
            R.id.iv_login_from_wechat, R.id.fl_login_root_view,R.id.img_key})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.input_delete:
                phoneNumber.setText("");
                break;
            case R.id.phone_number:
                break;
            case R.id.img_key_delete:
                key.setText("");
                break;
            case R.id.key:
                break;
            case R.id.imageview_piccheck:
                imageviewPiccheck.setImageBitmap(CodeUtils.getInstance().createBitmap());
                break;
            case R.id.piccheck_number:
                break;
            case R.id.tv_login:
                clearFocus();
                loginClick();
                break;
            case R.id.iv_login_from_qq:
                MyToast.showToast(this,"QQ登录成功!");
                break;
            case R.id.iv_login_from_sina:
                MyToast.showToast(this,"新浪微博登录成功!");
                break;
            case R.id.iv_login_from_wechat:
                MyToast.showToast(this,"微信登录成功!");
                break;
            case R.id.fl_login_root_view:
                clearFocus();
                break;
            case R.id.img_key:
                showKeyOrNot();
                break;
        }
    }

    private void loginClick() {
        if(Constants.isFastDoubleClick()){
            return;
        }
        String input = piccheckNumber.getText().toString();
        String num = CodeUtils.getInstance().getCode();
        if(!input.equals(num)){
            imageviewPiccheck.setImageBitmap(CodeUtils.getInstance().createBitmap());
            MyToast.showToast(this,"图形验证码错误！");
            return;
        }
        MyToast.showToast(this,"登录成功!");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },1000);
    }

    /**
     * 是否显示密码明文
     */
    private void showKeyOrNot() {
        if (!showKey) {
            imgKey.setImageResource(R.mipmap.login_icon_eye_click);
            key.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            Editable etable = key.getText();
            Selection.setSelection(etable, etable.length());
            showKey = true;
        } else {
            showKey = false;
            imgKey.setImageResource(R.mipmap.login_icon_eye_default);
            key.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            Editable etable = key.getText();
            Selection.setSelection(etable, etable.length());
        }
        //mKeyDeleteImg.setVisibility(View.GONE);
    }

    private void clearFocus(){
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(this.getCurrentFocus() != null){
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        key.setFocusable(false);
        phoneNumber.setFocusable(false);
        piccheckNumber.setFocusable(false);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 更新UI
            switch (msg.what) {
                case 2:
                    if (phoneNumber.getText().length() > 0
                            && !"请输入账号".equals(phoneNumber.getText())
                            && key.getText().length() > 0
                            && !"请输入密码".equals(key.getText())
                            && piccheckNumber.getText().length() > 0
                            && !"请输入图形验证码".equals(piccheckNumber.getText())
                            ) {
                        tvLogin.setTextColor(Color.parseColor("#ffffff"));//64b5f0
                        tvLogin.setEnabled(true);
                    } else {
                        tvLogin.setTextColor(Color.parseColor("#64b5f0"));
                        tvLogin.setEnabled(false);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 改变登录按钮状态
     */
    public void changeBtnStatus() {
        Message message = new Message();
        message.what = 2;
        mHandler.sendMessage(message);
    }

    private TextWatcher mPhoneNumWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            changeBtnStatus();
            if (phoneNumber.getText().length() > 0 && phoneNumber.getText().length() < 32) {
                inputDelete.setVisibility(View.VISIBLE);
            } else {
                inputDelete.setVisibility(View.GONE);
            }
        }
    };

    private TextWatcher mKeyWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            changeBtnStatus();
            if (key.getText().length() > 0 && key.getText().length() < 32) {
                imgKeyDelete.setVisibility(View.VISIBLE);
            } else {
                imgKeyDelete.setVisibility(View.GONE);
            }
        }
    };

    private TextWatcher checkWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            changeBtnStatus();
        }
    };

    private View.OnFocusChangeListener mFocusChangeForKey = new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View arg0, boolean arg1) {
            EditText text = phoneNumber;
            String str = text.getText().toString();
            if (arg1) {
                if (!TextUtils.isEmpty(str)) {
                    inputDelete.setVisibility(View.VISIBLE);
                } else {
                    inputDelete.setVisibility(View.GONE);
                }
            } else {
                inputDelete.setVisibility(View.GONE);
            }
        }
    };

    private View.OnFocusChangeListener mFocusListenerChangeForphoneNumber = new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View arg0, boolean arg1) {
            EditText text = phoneNumber;
            String str = text.getText().toString();
            if (arg1) {
                if (str.equals("请输入号码")) {
                    text.setText("");
                    inputDelete.setVisibility(View.GONE);
                } else if (str.equals("")) {
                    inputDelete.setVisibility(View.GONE);
                } else {
                    inputDelete.setVisibility(View.VISIBLE);
                }

            } else {
                inputDelete.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        String strphoneNumber = phoneNumber.getText().toString();
        switch (v.getId()) {
            case R.id.key:
                clearFocus();
                if(keyboardUtil == null){
                    keyboardUtil = new KeyboardUtil(this, this, key);
                }
                keyboardUtil.showKeyboard();
                break;
            case R.id.phone_number:
                phoneNumber.setFocusable(true);
                phoneNumber.setFocusableInTouchMode(true);
                phoneNumber.requestFocus();
                if(keyboardUtil != null){
                    keyboardUtil.hideKeyboard();
                }
                piccheckNumber.clearFocus();
                if (strphoneNumber.equals("请输入账号")) {
                    phoneNumber.setText("");
                    inputDelete.setVisibility(View.GONE);
                } else if (strphoneNumber.equals("")) {
                    inputDelete.setVisibility(View.GONE);
                } else {
                    inputDelete.setVisibility(View.VISIBLE);
                }

                imm.showSoftInput(phoneNumber, 0);
                if (android.os.Build.VERSION.SDK_INT <= 10) {
                    phoneNumber.setInputType(InputType.TYPE_NULL);
                } else {
                    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    try {
                        Class<EditText> cls = EditText.class;
                        if (android.os.Build.VERSION.SDK_INT < 16) {
                            Method setSoftInputShownOnFocus;
                            setSoftInputShownOnFocus = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                            setSoftInputShownOnFocus.setAccessible(true);
                            setSoftInputShownOnFocus.invoke(phoneNumber, false);
                        } else {
                            Method setShowSoftInputOnFocus;
                            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                            setShowSoftInputOnFocus.setAccessible(false);
                            setShowSoftInputOnFocus.invoke(phoneNumber, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.piccheck_number:
                piccheckNumber.setFocusable(true);
                piccheckNumber.setFocusableInTouchMode(true);
                piccheckNumber.requestFocus();
                phoneNumber.clearFocus();
                if(keyboardUtil != null){
                    keyboardUtil.hideKeyboard();
                }
                imm.showSoftInput(piccheckNumber, 0);
                if (android.os.Build.VERSION.SDK_INT <= 10) {
                    piccheckNumber.setInputType(InputType.TYPE_NULL);
                } else {
                    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    try {
                        Class<EditText> cls = EditText.class;
                        if (android.os.Build.VERSION.SDK_INT < 16) {
                            Method setSoftInputShownOnFocus;
                            setSoftInputShownOnFocus = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                            setSoftInputShownOnFocus.setAccessible(true);
                            setSoftInputShownOnFocus.invoke(piccheckNumber, false);
                        } else {
                            Method setShowSoftInputOnFocus;
                            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                            setShowSoftInputOnFocus.setAccessible(false);
                            setShowSoftInputOnFocus.invoke(piccheckNumber, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }
}
