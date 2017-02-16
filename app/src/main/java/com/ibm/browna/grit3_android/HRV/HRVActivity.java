package com.ibm.browna.grit3_android.HRV;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


import com.androidplot.xy.*;

import com.ibm.browna.grit3_android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;



public class HRVActivity extends ActionBarActivity implements OnItemSelectedListener, Observer {


    private int MAX_SIZE = 10; //graph max size
    boolean searchBt = true;
    BluetoothAdapter mBluetoothAdapter;
    List<BluetoothDevice> pairedDevices = new ArrayList<>();
    boolean menuBool = false; //display or not the disconnect option
    private XYPlot plot;
    boolean h7 = false; //Was the BTLE tested
    boolean normal = false; //Was the BT tested
    private Spinner spinner1;
    private Spinner fileSpinner;
    private Context context;
   // private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
  //  private ActionBarDrawerToggle mDrawerToggle;
   // private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    LineAndPointFormatter series1Format;
    // private LineChart mChart;

    public Context getContext() {
        if (context == null) {
            context = this.getApplicationContext();
        }
        return context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_hrv);
        Log.i("Main Activity", "Starting Polar HR monitor main activity");
        DataHandler.getInstance().addObserver(this);
        //Verify if device is to old for BTLE
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {

            Log.i("Main Activity", "old device H7 disbled");
            h7 = true;
        }

        //verify if bluetooth device are enabled
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (DataHandler.getInstance().newValue) {
            //Verify if bluetooth if activated, if not activate it
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter != null) {
                if (!mBluetoothAdapter.isEnabled()) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.bluetooth)
                            .setMessage(R.string.bluetoothOff)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mBluetoothAdapter.enable();
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    listBT();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    searchBt = false;
                                }
                            })
                            .show();
                } else {
                    listBT();
                }
            }

            plot = (XYPlot) findViewById(R.id.dynamicPlot);



            if (DataHandler.getInstance().getSeries1() == null)

            {
                Number[] series1Numbers = {};
                DataHandler.getInstance().setSeries1(new SimpleXYSeries(Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Heart Beat"));
            }






            DataHandler.getInstance().setNewValue(false);



        } else {
            listBT();
         //   mChart = (LineChart) findViewById(R.id.dynamicPlot);


            plot = (XYPlot) findViewById(R.id.dynamicPlot);


        }
        //LOAD Graph



        series1Format = new LineAndPointFormatter(Color.rgb(0, 0, 255), Color.rgb(200, 200, 200), null, null);
        series1Format.setPointLabelFormatter(new PointLabelFormatter(Color.RED));
        series1Format.setInterpolationParams( new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        plot.addSeries(DataHandler.getInstance().getSeries1(), series1Format);

        plot.getGraph().getDomainGridLinePaint().setColor(Color.TRANSPARENT);
        plot.getGraph().getRangeGridLinePaint().setColor(Color.TRANSPARENT);

        // remove the background stuff.
         //   plot.setBackgroundPaint(null);
        //    plot.getGraphWidget().setBackgroundPaint(null);
         //   plot.getGraph().set
      //  plot.getGraph().setGridBackgroundPaint();

       // plot.setRangeBoundaries(20,90,BoundaryMode.SHRINK);
        plot.setRangeLabel("Heart Beat");

     // plot.setDomainStep(StepMode.INCREMENT_BY_VAL,0);
        // plot.setTicksPerRangeLabel(3);
     //   plot.getG.setDomainLabelOrientation(-45);
        /**
        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);
        mChart.invalidate();
         **/


    }

    /**
     * Run on startup to list bluetooth paired device
     */
    public void listBT() {
        Log.d("Main Activity", "Listing BT elements");
        if (searchBt) {
            //Discover bluetooth devices
            final List<String> list = new ArrayList<>();
            list.add("");
            pairedDevices.addAll(mBluetoothAdapter.getBondedDevices());
            // If there are paired devices
            if (pairedDevices.size() > 0) {
                // Loop through paired devices
                for (BluetoothDevice device : pairedDevices) {
                    // Add the name and address to an array adapter to show in a ListView

                    if (device.getName().contains("Polar")) {
                        list.add(device.getName() + "\n" + device.getAddress());
                    }
                }
            }
            if (!h7) {
                Log.d("Main Activity", "Listing BTLE elements");
                final BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
                    public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
                        if (!list.contains(device.getName() + "\n" + device.getAddress())) {
                            Log.d("Main Activity", "Adding " + device.getName());
                            list.add(device.getName() + "\n" + device.getAddress());
                            pairedDevices.add(device);
                        }
                    }
                };


                Thread scannerBTLE = new Thread() {
                    public void run() {
                        Log.d("Main Activity", "Starting scanning for BTLE");
                        mBluetoothAdapter.startLeScan(leScanCallback);
                        try {
                            Thread.sleep(5000);
                            Log.d("Main Activity", "Stoping scanning for BTLE");
                            mBluetoothAdapter.stopLeScan(leScanCallback);
                        } catch (InterruptedException e) {
                            Log.e("Main Activity", "ERROR: on scanning");
                        }
                    }
                };

                scannerBTLE.start();
            }

            //Populate drop down
            spinner1 = (Spinner) findViewById(R.id.HRV_device_spinner1);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setOnItemSelectedListener(this);
            spinner1.setAdapter(dataAdapter);


            if (DataHandler.getInstance().getID() != 0 && DataHandler.getInstance().getID() < spinner1.getCount())
                spinner1.setSelection(DataHandler.getInstance().getID());
        }
    }

    /**
     * When menu button are pressed
     */
    public boolean onOptionsItemSelected(MenuItem item) {

        /**
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
         **/

        int id = item.getItemId();
        Log.d("Main Activity", "Menu pressed");
        if (id == R.id.action_settings) { //close connection
            menuBool = false;
            Log.d("Main Activity", "disable pressed");
            if (spinner1 != null) {
                spinner1.setSelection(0);
            }
            if (DataHandler.getInstance().getReader() == null) {
                Log.i("Main Activity", "Disabling h7");
                DataHandler.getInstance().getH7().cancel();
                DataHandler.getInstance().setH7(null);
                h7 = false;
            } else {
                Log.i("Main Activity", "Disabling BT");
                DataHandler.getInstance().getReader().cancel();
                DataHandler.getInstance().setReader(null);
                normal = false;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * When the option is selected in the dropdown we turn on the bluetooth
     */
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (pos != 0) {
                //Actual work
                DataHandler.getInstance().setID(pos);
                if (!h7 && ((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1]).getName().contains("H7") && DataHandler.getInstance().getReader() == null) {

                    Log.i("Main Activity", "Starting h7");
                    DataHandler.getInstance().setH7(new H7ConnectThread((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1], this));
                    h7 = true;
                } else if (!normal && DataHandler.getInstance().getH7() == null) {

                    Log.i("Main Activity", "Starting normal");
                    DataHandler.getInstance().setReader(new ConnectThread((BluetoothDevice) pairedDevices.toArray()[pos - 1], this));
                    DataHandler.getInstance().getReader().start();
                    normal = true;
                }
                menuBool = true;
            }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setEnabled(menuBool);
        menu.findItem(R.id.action_settings).setVisible(menuBool);
        return true;
    }

    public void onNothingSelected(AdapterView<?> arg0) {    }

    /**
     * Called when bluetooth connection failed
     */
    public void connectionError() {

        Log.w("Main Activity", "Connection error occured");
        if (menuBool) {//did not manually tried to disconnect
            Log.d("Main Activity", "in the app");
            menuBool = false;
            final HRVActivity ac = this;
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getBaseContext(), getString(R.string.couldnotconnect), Toast.LENGTH_SHORT).show();
                    //TextView rpm = (TextView) findViewById(R.id.rpm);
                    //rpm.setText("0 BMP");
                    Spinner spinner1 = (Spinner) findViewById(R.id.HRV_device_spinner1);
                    if (DataHandler.getInstance().getID() < spinner1.getCount())
                        spinner1.setSelection(DataHandler.getInstance().getID());
                    if (!h7) {
                        Log.w("Main Activity", "starting H7 after error");
                        DataHandler.getInstance().setReader(null);
                        DataHandler.getInstance().setH7(new H7ConnectThread((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1], ac));
                        h7 = true;
                    } else if (!normal) {
                        Log.w("Main Activity", "Starting normal after error");
                        DataHandler.getInstance().setH7(null);
                        DataHandler.getInstance().setReader(new ConnectThread((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1], ac));
                        DataHandler.getInstance().getReader().start();
                        normal = true;
                    }
                }
            });
        }
    }

    public void update(Observable observable, Object data) {
        receiveData();
    }

    /**
     * Update Gui with new value from receiver
     */
    public void receiveData() {

        runOnUiThread(new Runnable() {
            public void run() {
                //menuBool=true;



               /**

                if (DataHandler.getInstance().getLastBPMIntValue() != 0) {
                 DataHandler.getInstance().getSeries1().addLast(0, DataHandler.getInstance().getLastBPMIntValue());

                 if (DataHandler.getInstance().getSeries1().size() > MAX_SIZE)
                 DataHandler.getInstance().getSeries1().removeFirst();//Prevent graph to overload data.

                 plot.redraw();
                 }

                if (DataHandler.getInstance().getmHRV() != 0) {
                **/



                if (DataHandler.getInstance().getLastBPMIntValue() != 0) {

                    int lastBPM = DataHandler.getInstance().getLastBPMIntValue();
                    DataHandler.getInstance().getSeries1().addLast(0, lastBPM);
                    if (DataHandler.getInstance().getSeries1().size() > MAX_SIZE) {
                        DataHandler.getInstance().getSeries1().removeFirst();//Prevent graph to overload data.
                    }


                    plot.redraw();

                    plot.setRangeBoundaries(lastBPM - 5, lastBPM + 5, BoundaryMode.FIXED);






                }






                TextView min = (TextView) findViewById(R.id.min);
                min.setText(DataHandler.getInstance().getMin());

                TextView avg = (TextView) findViewById(R.id.avg);
                avg.setText(DataHandler.getInstance().getAvg());

                TextView max = (TextView) findViewById(R.id.max);
                max.setText(DataHandler.getInstance().getMax());



                if (DataHandler.getInstance().getmHRV() > 0) {
                    TextView textHRV = (TextView) findViewById(R.id.hrv);
                    textHRV.setText("HRV: " + String.valueOf(DataHandler.getInstance().getmHRV()));
                }


            }
        });
    }


    public void onStop() {
        super.onStop();
    }


    /**
    private void setupDrawer() {
       mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
            }
            /** Called when a drawer has settled in a completely closed state.
            public void onDrawerClosed(View view) {
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }



    public void onDrawerClosed(View view) {
        onDrawerClosed(view);
        getActionBar().setTitle(mActivityTitle);
        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
    }

    public void onDrawerOpened(View drawerView) {
        onDrawerOpened(drawerView);
        getActionBar().setTitle("Navigation!");
        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
    }
    private void addDrawerItems() {
        String[] navArray = { "Level Set", "Values","Goals", "HRV", "ToneAnalyzer"};
        mAdapter = new ArrayAdapter<String>(this, R.layout.list_item_nav, navArray);
        mDrawerList.setAdapter(mAdapter);
    }
     **/

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
       // mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
       // mDrawerToggle.onConfigurationChanged(newConfig);
    }

}
