package com.xiaocool.sugarangel.utils;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * fragment切换用工具类
 */

public class FragmentSwitcher {
    private FragmentManager manager;
    private int containerViewId;
    private Fragment currentFragment;

    private List<Pair<String, Fragment>> fragmentList = new ArrayList<>();
//    private List<FragmentProvider> providerList = new ArrayList<>();
    private List<Pair<String, FragmentProvider>> providerList = new ArrayList<>();

    public FragmentSwitcher(FragmentManager manager, @IdRes int containerViewId) {
        this.manager = manager;
        this.containerViewId = containerViewId;
    }

    public FragmentSwitcher addFragment(String tag, Fragment fragment) {
        fragmentList.add(new Pair<>(tag, fragment));
        return this;
    }

    public FragmentSwitcher addFragment(String tag, FragmentProvider provider) {
        providerList.add(new Pair<>(tag, provider));
        return this;
    }

    public void showFragment(String tag) {
        FragmentTransaction transition = manager.beginTransaction();
        try {
            if (currentFragment != null) {
                transition.hide(currentFragment);
            }
            Fragment fragment = manager.findFragmentByTag(tag);
            if (fragment == null) {
                fragment = findFragmentByTag(tag);
                transition.add(containerViewId, fragment, tag);
            } else {
                transition.show(fragment);
            }
            currentFragment = fragment;
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } finally {
            transition.commit();
        }
    }

    private Fragment findFragmentByTag(String tag) throws IllegalAccessException {
        for (Pair<String, Fragment> pair : fragmentList) {
            if (pair.first.equals(tag))
                return pair.second;
        }

        for (Pair<String, FragmentProvider> pair : providerList) {
            if (pair.first.equals(tag)) {
                Fragment fragment = pair.second.getFragment();
                fragmentList.add(new Pair<>(tag, fragment));
                return fragment;
            }
        }

        throw new IllegalAccessException("did not find " + tag + " corresponding Fragment");
    }

    public interface FragmentProvider {
        Fragment getFragment();
    }
}
