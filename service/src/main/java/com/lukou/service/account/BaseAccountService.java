package com.lukou.service.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.lukou.service.account.bean.User;
import com.lukou.service.utils.GsonManager;

import java.util.ArrayList;

/**
 * @author Xu
 */
public abstract class BaseAccountService implements AccountService {

    private final ArrayList<AccountListener> listeners = new ArrayList<>();

    protected Context context;

    protected SharedPreferences prefs;

    public BaseAccountService(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("account", Context.MODE_PRIVATE);
    }

    @Override
    public User user() {
        String userJsonString = prefs.getString("user", null);
        if (TextUtils.isEmpty(userJsonString)) {
            return null;
        }
        return GsonManager.instance().fromJson(userJsonString, User.class);
    }

    /**
     * @return
     */
    @Override
    public long id() {
        return prefs.getLong("uid", 0);
    }

    @Override
    public String token() {
        return prefs.getString("token", "");
    }

    @Override
    public void logout() {
        User olderUser = user();
        prefs.edit()
                .remove("uid")
                .remove("token")
                .remove("user")
                .apply();
        dispatchAccountChanged(olderUser);
    }

    @Override
    public void saveUser(User user) {
        User oldUser = user();
        prefs.edit()
                .putLong("uid", user.getId())
                .putString("user", user.toJsonString())
                .apply();
        dispatchAccountChanged(oldUser);
    }

    @Override
    public void saveToken(String token) {
        prefs.edit()
                .putString("token", token)
                .apply();
    }

    @Override
    public void update(User user) {
        prefs.edit()
                .putLong("uid", user.getId())
                .putString("user", user.toJsonString())
                .apply();
        dispatchProfileChanged();
    }

    private void dispatchAccountChanged(User oldUser) {
        AccountListener[] listenerArray = listeners.toArray(new AccountListener[0]);
        for (AccountListener listener : listenerArray) {
            if (listener == null) continue;
            listener.onAccountChanged(this, oldUser);
        }
    }

    private void dispatchProfileChanged() {
        AccountListener[] listenerArray = listeners.toArray(new AccountListener[0]);
        for (AccountListener listener : listenerArray) {
            if (listener == null) continue;
            listener.onProfileChanged(this);
        }
    }

    @Override
    public void addListener(AccountListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
        if (listener != null) {
            listeners.add(listener);
        }
    }

    @Override
    public void removeListener(AccountListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    @Override
    public boolean isLogin() {
        return id() != 0 && !TextUtils.isEmpty(token());
    }

}
