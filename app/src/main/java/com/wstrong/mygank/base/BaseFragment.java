/*
 * {EasyGank}  Copyright (C) {2015}  {CaMnter}
 *
 * This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; type `show c' for details.
 *
 * The hypothetical commands `show w' and `show c' should show the appropriate
 * parts of the General Public License.  Of course, your program's commands
 * might be different; for a GUI interface, you would use an "about box".
 *
 * You should also get your employer (if you work as a programmer) or school,
 * if any, to sign a "copyright disclaimer" for the program, if necessary.
 * For more information on this, and how to apply and follow the GNU GPL, see
 * <http://www.gnu.org/licenses/>.
 *
 * The GNU General Public License does not permit incorporating your program
 * into proprietary programs.  If your program is a subroutine library, you
 * may consider it more useful to permit linking proprietary applications with
 * the library.  If this is what you want to do, use the GNU Lesser General
 * Public License instead of this License.  But first, please read
 * <http://www.gnu.org/philosophy/why-not-lgpl.html>.
 */

package com.wstrong.mygank.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description：BaseFragment
 */
public abstract class BaseFragment extends Fragment {

    protected View rootView;

    protected BaseAppCompatActivity baseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        baseActivity = (BaseAppCompatActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        baseActivity = null;
    }

    /**
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (this.rootView == null) {
            this.rootView = inflater.inflate(this.getLayoutId(), container, false);
        }

        if (this.rootView.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.rootView.getParent();
            parent.removeView(this.rootView);
        }

        this.initViews(this.rootView, savedInstanceState);
        this.initData();
        this.initListeners();
        return this.rootView;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    /**
     * Fill in layout id
     *
     * @return layout id
     */
    protected abstract int getLayoutId();

    /**
     * Initialize the view in the layout
     *
     * @param self self
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initViews(View self, Bundle savedInstanceState);

    /**
     * Initialize the View of the listener
     */
    protected abstract void initListeners();

    /**
     * Initialize the Activity data
     */
    protected abstract void initData();


    /**
     * Find the view by id
     *
     * @param id id
     * @param <V> V
     * @return V
     */
    @SuppressWarnings("unchecked") protected <V extends View> V findView(int id) {
        return (V) this.rootView.findViewById(id);
    }


    public void showToast(String message) {
        baseActivity.showToast(message);
    }

    public void showToast(int messageId) {
        showToast(getString(messageId));
    }

    public void showProgressDialog() {
        showProgressDialog("");
    }

    public void showProgressDialog(String message) {
        showProgressDialog(message,true);
    }

    public void showProgressDialog(String message, boolean cancelable) {
        baseActivity.showProgressDialog(message,cancelable);
    }

    public void stopProgressDialog() {
        baseActivity.stopProgressDialog();
    }


    protected BaseAppCompatActivity getBaseActivity(){
        return baseActivity;
    }
}
