package com.example.covidnews.viewmodels;

import android.os.Looper;

import androidx.lifecycle.ViewModel;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.os.Handler;
import java.util.logging.LogRecord;

public class TaskRunner {
    private final Executor executor = Executors.newSingleThreadExecutor(); // change according to your requirements
}

class getCoronainfo{
    private final String api_des = "https://raw.githubusercontent.com/chrislopez24/corona-parser/master/cases.json";
    

}