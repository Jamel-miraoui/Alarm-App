package com.example.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

public class MonServive extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
// Placez ici le code qui sera exécuté lors de la création de ce service
    }

    @Override
    public void onStart(Intent intent, int startId) {
// Placez ici le code qui sera exécuté à chaque démarrage du service
    }

    @Override
    public void onDestroy() {
// Placez ici le code qui sera exécuté lors de la destruction de ce service
    }


}
