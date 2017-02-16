package com.ibm.browna.grit3_android.HRVFragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;

import com.ibm.browna.grit3_android.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by browna on 2/10/2017.
 */

public class HRVFragment extends Fragment implements /*AdapterView.OnItemSelectedListener, */Observer {

    private int MAX_SIZE = 10; //graph max size
    boolean searchBt = true;
    BluetoothAdapter mBluetoothAdapter;
    List<BluetoothDevice> pairedDevices = new ArrayList<>();
    boolean menuBool = false; //display or not the disconnect option
    private XYPlot plot;
    private TextView mHrvText;
    boolean h7 = false; //Was the BTLE tested
    boolean normal = false; //Was the BT tested
    private Spinner spinner1;
    private Spinner fileSpinner;
    private Context context;
    ArrayList<String> list;
    LineAndPointFormatter series1Format;
    View mParent;


    //

    public Context getContext() {
        if (context == null) {
            context = getActivity().getApplicationContext();
        }
        return context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_hrv,container,false);
        mParent = v;
        context = getActivity().getApplicationContext();
        Log.e("HRV Activity", "Starting Polar HR monitor HRV Fragment ");
        DataHandler.getInstance().addObserver(this);
        //Verify if device is to old for BTLE
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {

            Log.e("HRV Fragment", "old device H7 disbled");
            h7 = true;
        }

        //verify if bluetooth device are enabled
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (DataHandler.getInstance().newValue) {
            //Verify if bluetooth if activated, if not activate it
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter != null) {
                if (!mBluetoothAdapter.isEnabled()) {
                    new AlertDialog.Builder(getActivity())
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

            plot = (XYPlot) v.findViewById(R.id.dynamicPlot);



            if (DataHandler.getInstance().getSeries1() == null)

            {
                Number[] series1Numbers = {};
                DataHandler.getInstance().setSeries1(new SimpleXYSeries(Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Heart Beat"));
            }


            DataHandler.getInstance().setNewValue(false);



        } else {
            listBT();
            //   mChart = (LineChart) findViewById(R.id.dynamicPlot);


            plot = (XYPlot) v.findViewById(R.id.dynamicPlot);


        }
        //LOAD Graph

        series1Format = new LineAndPointFormatter(Color.rgb(0, 0, 255), Color.rgb(200, 200, 200), null, null);

        series1Format.setPointLabelFormatter(new PointLabelFormatter(Color.RED));
        series1Format.setInterpolationParams( new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));



        plot.addSeries(DataHandler.getInstance().getSeries1(), series1Format);

        plot.getGraph().getDomainGridLinePaint().setColor(Color.TRANSPARENT);
        plot.getGraph().getRangeGridLinePaint().setColor(Color.TRANSPARENT);

        plot.setRangeLabel("Heart Beat");


        plot.setVisibility(View.INVISIBLE);
        mHrvText = (TextView) mParent.findViewById(R.id.hrv);
        mHrvText.setVisibility(View.INVISIBLE);

        return v;
    }

    /**
     * Run on startup to list bluetooth paired device
     */
    public void listBT() {
        Log.e("HRV Fragment", "Listing BT elements");
        final HRVFragment ac =this;
        new AsyncTask<Void, Void,ArrayList<String>>(){
            @Override
            protected ArrayList<String> doInBackground(Void... voids) {
                if (searchBt) {
                    //Discover bluetooth devices
                    list = new ArrayList<>();
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
                        Log.e("HRV Fragment", "Listing BTLE elements");
                        final BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
                            public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
                                if (!list.contains(device.getName() + "\n" + device.getAddress())) {
                                    Log.d("HRV Fragment", "Adding " + device.getName());
                                    list.add(device.getName() + "\n" + device.getAddress());
                                    pairedDevices.add(device);
                                }
                            }
                        };

                        Log.e("HRV Fragment", "Starting scanning for BTLE");
                        mBluetoothAdapter.startLeScan(leScanCallback);
                        try {
                            Thread.sleep(5000);
                            Log.e("HRV Fragment", "Stoping scanning for BTLE");
                            mBluetoothAdapter.stopLeScan(leScanCallback);
                        } catch (InterruptedException e) {
                            Log.e("HRV Fragment", "ERROR: on scanning");
                        }


                    }
                    return list;
                }
                return new ArrayList<>();
            }

            @Override
            protected void onPostExecute(ArrayList<String> strings) {
                super.onPostExecute(strings);
                //Populate drop down
                spinner1 = (Spinner) mParent.findViewById(R.id.HRV_device_spinner1);

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //dataAdapter.setDropDownViewTheme();
                // spinner1.setOnItemSelectedListener(this);
                spinner1.setAdapter(dataAdapter);
                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != 0) {
                            plot.setVisibility(View.VISIBLE);
                            mHrvText.setVisibility(View.VISIBLE);
                            //Actual work
                            DataHandler.getInstance().setID(i);
                            if (!h7 && ((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1]).getName().contains("H7") && DataHandler.getInstance().getReader() == null) {

                                Log.e("HRV Fragment", "Starting h7");
                                DataHandler.getInstance().setH7(new H7ConnectThread((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1],ac ));
                                h7 = true;
                            } else if (!normal && DataHandler.getInstance().getH7() == null) {

                                Log.e("HRV Fragment", "Starting normal");
                                DataHandler.getInstance().setReader(new ConnectThread((BluetoothDevice) pairedDevices.toArray()[i - 1], ac));
                                DataHandler.getInstance().getReader().start();
                                normal = true;
                            }
                            menuBool = true;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                if (DataHandler.getInstance().getID() != 0 && DataHandler.getInstance().getID() < spinner1.getCount())
                    spinner1.setSelection(DataHandler.getInstance().getID());

            }
        }.execute();
       /** if (searchBt) {
            //Discover bluetooth devices
            list = new ArrayList<>();
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
                Log.e("HRV Fragment", "Listing BTLE elements");
                final BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
                    public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
                        if (!list.contains(device.getName() + "\n" + device.getAddress())) {
                            Log.d("HRV Fragment", "Adding " + device.getName());
                            list.add(device.getName() + "\n" + device.getAddress());
                            pairedDevices.add(device);
                        }
                    }
                };

                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {
                        Log.e("HRV Fragment", "Starting scanning for BTLE");
                        mBluetoothAdapter.startLeScan(leScanCallback);
                        try {
                            Thread.sleep(5000);
                            Log.e("HRV Fragment", "Stoping scanning for BTLE");
                            mBluetoothAdapter.stopLeScan(leScanCallback);
                        } catch (InterruptedException e) {
                            Log.e("HRV Fragment", "ERROR: on scanning");
                        }
                        return null;
                    }
                }.execute();

            }

            //Populate drop down
            spinner1 = (Spinner) mParent.findViewById(R.id.HRV_device_spinner1);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //dataAdapter.setDropDownViewTheme();
           // spinner1.setOnItemSelectedListener(this);
            spinner1.setAdapter(dataAdapter);
            final HRVFragment ac = this;
            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        plot.setVisibility(View.VISIBLE);
                        mHrvText.setVisibility(View.VISIBLE);
                        //Actual work
                        DataHandler.getInstance().setID(i);
                        if (!h7 && ((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1]).getName().contains("H7") && DataHandler.getInstance().getReader() == null) {

                            Log.e("HRV Fragment", "Starting h7");
                            DataHandler.getInstance().setH7(new H7ConnectThread((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1],ac ));
                            h7 = true;
                        } else if (!normal && DataHandler.getInstance().getH7() == null) {

                            Log.e("HRV Fragment", "Starting normal");
                            DataHandler.getInstance().setReader(new ConnectThread((BluetoothDevice) pairedDevices.toArray()[i - 1], ac));
                            DataHandler.getInstance().getReader().start();
                            normal = true;
                        }
                        menuBool = true;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            if (DataHandler.getInstance().getID() != 0 && DataHandler.getInstance().getID() < spinner1.getCount())
                spinner1.setSelection(DataHandler.getInstance().getID());
        */
    }


    /**
     * When menu button are pressed
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        Log.d("HRV Fragment", "Menu pressed");
        if (id == R.id.action_settings) { //close connection
            menuBool = false;
            Log.d("HRV Fragment", "disable pressed");
            if (spinner1 != null) {
                spinner1.setSelection(0);
            }
            if (DataHandler.getInstance().getReader() == null) {
                Log.i("HRV Fragment", "Disabling h7");
                DataHandler.getInstance().getH7().cancel();
                DataHandler.getInstance().setH7(null);
                h7 = false;
            } else {
                Log.i("HRV Fragment", "Disabling BT");
                DataHandler.getInstance().getReader().cancel();
                DataHandler.getInstance().setReader(null);
                normal = false;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * When the option is selected in the dropdown we turn on the bluetooth
     */

   /* public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (pos != 0) {
            //Actual work
            DataHandler.getInstance().setID(pos);
            if (!h7 && ((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1]).getName().contains("H7") && DataHandler.getInstance().getReader() == null) {

                Log.i("HRV Fragment", "Starting h7");
                DataHandler.getInstance().setH7(new H7ConnectThread((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1], this));
                h7 = true;
            } else if (!normal && DataHandler.getInstance().getH7() == null) {

                Log.i("HRV Fragment", "Starting normal");
                DataHandler.getInstance().setReader(new ConnectThread((BluetoothDevice) pairedDevices.toArray()[pos - 1], this));
                DataHandler.getInstance().getReader().start();
                normal = true;
            }
            menuBool = true;
        }
    }*/

//    public boolean onPrepareOptionsMenu(Menu menu) {
//        menu.findItem(R.id.action_settings).setEnabled(menuBool);
//        menu.findItem(R.id.action_settings).setVisible(menuBool);
//        return true;
//    }

    public void onNothingSelected(AdapterView<?> arg0) {    }

    /**
     * Called when bluetooth connection failed
     */
    public void connectionError() {

        Log.e("HRV Fragment", "Connection error occured");
        if (menuBool) {//did not manually tried to disconnect
            Log.e("HRV Fragment", "in the app");
            menuBool = false;
            final HRVFragment ac = this;

            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, getString(R.string.couldnotconnect), Toast.LENGTH_SHORT).show();
                    //TextView rpm = (TextView) findViewById(R.id.rpm);
                    //rpm.setText("0 BMP");
                    Spinner spinner1 = (Spinner) mParent.findViewById(R.id.HRV_device_spinner1);
                    if (DataHandler.getInstance().getID() < spinner1.getCount())
                        spinner1.setSelection(DataHandler.getInstance().getID());
                    if (!h7) {
                        Log.e("HRV Fragment", "starting H7 after error");
                        DataHandler.getInstance().setReader(null);
                        DataHandler.getInstance().setH7(new H7ConnectThread((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1], ac));
                        h7 = true;
                    } else if (!normal) {
                        Log.e("HRV Fragment", "Starting normal after error");
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
        new AsyncTask<Void,Void,Integer>(){
            @Override
            protected Integer doInBackground(Void... voids) {
                if (DataHandler.getInstance().getLastBPMIntValue() != 0) {

                    int lastBPM = DataHandler.getInstance().getLastBPMIntValue();
                    DataHandler.getInstance().getSeries1().addLast(0, lastBPM);
                    if (DataHandler.getInstance().getSeries1().size() > MAX_SIZE) {
                        DataHandler.getInstance().getSeries1().removeFirst();//Prevent graph to overload data.
                    }
                    return lastBPM;

                }
                return 0;
            }

            @Override
            protected void onPostExecute(Integer lastBPM) {
                super.onPostExecute(lastBPM );
                plot.redraw();
                plot.setRangeBoundaries(lastBPM - 5, lastBPM + 5, BoundaryMode.FIXED);
                if (DataHandler.getInstance().getmHRV() > 0) {

                    mHrvText.setText("HRV: " + String.valueOf(DataHandler.getInstance().getmHRV()));
                }
            }
        }.execute();

        /*getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (DataHandler.getInstance().getLastBPMIntValue() != 0) {

                    int lastBPM = DataHandler.getInstance().getLastBPMIntValue();
                    DataHandler.getInstance().getSeries1().addLast(0, lastBPM);
                    if (DataHandler.getInstance().getSeries1().size() > MAX_SIZE) {
                        DataHandler.getInstance().getSeries1().removeFirst();//Prevent graph to overload data.
                    }
                    plot.redraw();
                    plot.setRangeBoundaries(lastBPM - 5, lastBPM + 5, BoundaryMode.FIXED);
                    if (DataHandler.getInstance().getmHRV() > 0) {
                        TextView textHRV = (TextView) mParent.findViewById(R.id.hrv);
                        textHRV.setText("HRV: " + String.valueOf(DataHandler.getInstance().getmHRV()));
                    }
                }
            }

        });*/



        /*getActivity().runOnUiThread(new Runnable() {
            public void run() {



                //TextView min = (TextView) mParent.findViewById(R.id.min);
               // min.setText(DataHandler.getInstance().getMin());

               // TextView avg = (TextView) mParent.findViewById(R.id.avg);
               // avg.setText(DataHandler.getInstance().getAvg());

               // TextView max = (TextView) mParent.findViewById(R.id.max);
               // max.setText(DataHandler.getInstance().getMax());



            }
        });*/
    }


}
