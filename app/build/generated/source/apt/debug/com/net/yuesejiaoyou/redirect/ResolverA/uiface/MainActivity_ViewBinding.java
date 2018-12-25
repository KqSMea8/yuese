// Generated code from Butter Knife. Do not modify!
package com.net.yuesejiaoyou.redirect.ResolverA.uiface;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.net.yuesejiaoyou.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131297259;

  private View view2131296715;

  private View view2131297608;

  private View view2131297584;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.shipinimg, "field 'ivFirst' and method 'firstClick'");
    target.ivFirst = Utils.castView(view, R.id.shipinimg, "field 'ivFirst'", ImageView.class);
    view2131297259 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.firstClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.guangchangimg, "field 'ivVideo' and method 'videoClick'");
    target.ivVideo = Utils.castView(view, R.id.guangchangimg, "field 'ivVideo'", ImageView.class);
    view2131296715 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.videoClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.xiaoxiimg, "field 'ivMessage' and method 'messageClick'");
    target.ivMessage = Utils.castView(view, R.id.xiaoxiimg, "field 'ivMessage'", ImageView.class);
    view2131297608 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.messageClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.wodeimg, "field 'ivMine' and method 'mineClick'");
    target.ivMine = Utils.castView(view, R.id.wodeimg, "field 'ivMine'", ImageView.class);
    view2131297584 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.mineClick();
      }
    });
    target.ivNew = Utils.findRequiredViewAsType(source, R.id.hd, "field 'ivNew'", ImageView.class);
    target.flContent = Utils.findRequiredViewAsType(source, R.id.fl_content, "field 'flContent'", FrameLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivFirst = null;
    target.ivVideo = null;
    target.ivMessage = null;
    target.ivMine = null;
    target.ivNew = null;
    target.flContent = null;

    view2131297259.setOnClickListener(null);
    view2131297259 = null;
    view2131296715.setOnClickListener(null);
    view2131296715 = null;
    view2131297608.setOnClickListener(null);
    view2131297608 = null;
    view2131297584.setOnClickListener(null);
    view2131297584 = null;
  }
}
