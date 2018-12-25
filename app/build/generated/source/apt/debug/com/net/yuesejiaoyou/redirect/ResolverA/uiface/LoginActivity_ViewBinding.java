// Generated code from Butter Knife. Do not modify!
package com.net.yuesejiaoyou.redirect.ResolverA.uiface;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.net.yuesejiaoyou.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131297609;

  private View view2131297077;

  private View view2131297568;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.xieyi, "field 'xieyi' and method 'xieyiClick'");
    target.xieyi = Utils.castView(view, R.id.xieyi, "field 'xieyi'", TextView.class);
    view2131297609 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.xieyiClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.phonelogin, "method 'phoneClick'");
    view2131297077 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.phoneClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.wc, "method 'wxClick'");
    view2131297568 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.wxClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.xieyi = null;

    view2131297609.setOnClickListener(null);
    view2131297609 = null;
    view2131297077.setOnClickListener(null);
    view2131297077 = null;
    view2131297568.setOnClickListener(null);
    view2131297568 = null;
  }
}
