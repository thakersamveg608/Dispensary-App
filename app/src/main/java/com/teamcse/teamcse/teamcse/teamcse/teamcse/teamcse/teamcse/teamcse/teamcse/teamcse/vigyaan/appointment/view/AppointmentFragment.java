package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dd.CircularProgressButton;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.R;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.model.AppointmentDataResponse;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.presenter.AppointmentPresenter;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.presenter.AppointmentPresenterImpl;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.provider.RetrofitAppointmentHelper;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.helper.SharedPrefs;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.hospital.HospitalFragment;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AppointmentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentFragment extends Fragment implements View.OnClickListener,AppointmentView{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Handler handler;

    private HospitalFragment.OnFragmentInteractionListener mListener;

    private TextView btnDatePicker, btnTimePicker;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String name,issue,type,date,time;

    @BindView(R.id.name)
    EditText patientName;
    @BindView(R.id.issue)
    EditText medicalIssue;
    @BindView(R.id.emergency_radio)
    RadioButton emergency;
    @BindView(R.id.regular_radio)
    RadioButton regular;
    @BindView(R.id.progressBar_appointment)
    ProgressBar progressBar;
    @BindView(R.id.appoint)
    Button appoint_click;
    @BindView(R.id.issue_type)
    RadioGroup issueType;
    @BindView(R.id.input_layout_name)
    TextInputLayout inputLayoutName;
    @BindView(R.id.input_layout_issue)
    TextInputLayout inputLayoutIssue;

    private RadioButton radioButton;
    private SharedPrefs sharedPrefs;

    Resources system;
    TimePicker timePicker;

    AppointmentPresenter appointmentPresenter;

    public AppointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentFragment newInstance(String param1, String param2) {
        AppointmentFragment fragment = new AppointmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_appointment,container,false);
        ButterKnife.bind(this,v);


//        btnDatePicker=(TextView)v.findViewById(R.id.text_date);
//        btnTimePicker=(TextView)v.findViewById(R.id.text_time);
        timePicker = (TimePicker) v.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(false);

        sharedPrefs = new SharedPrefs(getContext());

        input(v);
        set_timepicker_text_colour(timePicker);

//        btnDatePicker.setOnClickListener(this);
//        btnTimePicker.setOnClickListener(this);

        appoint_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=patientName.getText().toString().trim();
                issue=medicalIssue.getText().toString().trim();
                // get selected radio button from radioGroup
                int selectedId = issueType.getCheckedRadioButtonId();
                radioButton = (RadioButton) v.findViewById(selectedId);
                type=radioButton.getText().toString();
//                time=mHour+":"+mMinute;
//                date=mYear+"-"+(mMonth+1)+"-"+mDay;

                if(!validateName() || !validateIssue() || Integer.toString(mHour).isEmpty() || Integer.toString(mYear).isEmpty()){
//                    Toast.makeText(getContext(), "One or more fields empty!", Toast.LENGTH_LONG).show();
                    MDToast mdToast = MDToast.makeText(getActivity(), "One or more fields empty", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING);
                    mdToast.show();
                }
                else
                {
                    appointmentPresenter = new AppointmentPresenterImpl(AppointmentFragment.this,new RetrofitAppointmentHelper());
                    appointmentPresenter.getAppointmentData(name,sharedPrefs.getAccessToken(),issue,date,time,type);
                    patientName.setHint("Patient's Full Name");
                    medicalIssue.setHint("Medical Issue");
                    btnDatePicker.setText("Date");
                    btnTimePicker.setText("Time");

                }
            }
        });

        return v;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

//        if (v == btnDatePicker) {
//
//            // Get Current Date
//            final Calendar c = Calendar.getInstance();
//            mYear = c.get(Calendar.YEAR);
//            mMonth = c.get(Calendar.MONTH);
//            mDay = c.get(Calendar.DAY_OF_MONTH);
//
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
//                    new DatePickerDialog.OnDateSetListener() {
//
//                        @Override
//                        public void onDateSet(DatePicker view, int year,
//                                              int monthOfYear, int dayOfMonth) {
//
//                            btnDatePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//
//                        }
//                    }, mYear, mMonth, mDay);
//            datePickerDialog.show();
//        }
//        if (v == btnTimePicker) {
//
//            // Get Current Time
//            final Calendar c = Calendar.getInstance();
//            mHour = c.get(Calendar.HOUR_OF_DAY);
//            mMinute = c.get(Calendar.MINUTE);
//
//            // Launch Time Picker Dialog
//            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
//                    new TimePickerDialog.OnTimeSetListener() {
//
//                        @Override
//                        public void onTimeSet(TimePicker view, int hourOfDay,
//                                              int minute) {
//
//                            btnTimePicker.setText(hourOfDay + ":" + minute);
//                        }
//                    }, mHour, mMinute, false);
//            timePickerDialog.show();
//        }

    }

    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showError(String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void showAppointmentStatus(AppointmentDataResponse appointmentDataResponse) {

        Toast.makeText(getContext(), "Appointment Successfull!!", Toast.LENGTH_LONG).show();

    }
    public void input(View rootview){

        patientName.addTextChangedListener(new MyTextWatcher(patientName));
        medicalIssue.addTextChangedListener(new MyTextWatcher(medicalIssue));
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 0);

/* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 2);
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(rootview, R.id.calendarView).range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                //do something
            }
        });
    }
    private void set_timepicker_text_colour(TimePicker timepicker){
        system = Resources.getSystem();
        int hour_numberpicker_id = system.getIdentifier("hour", "id", "android");
        int minute_numberpicker_id = system.getIdentifier("minute", "id", "android");
        int ampm_numberpicker_id = system.getIdentifier("amPm", "id", "android");

        NumberPicker hour_numberpicker = (NumberPicker) timepicker.findViewById(hour_numberpicker_id);
        NumberPicker minute_numberpicker = (NumberPicker) timepicker.findViewById(minute_numberpicker_id);
        NumberPicker ampm_numberpicker = (NumberPicker) timepicker.findViewById(ampm_numberpicker_id);

        set_numberpicker_text_colour(hour_numberpicker);
        set_numberpicker_text_colour(minute_numberpicker);
        set_numberpicker_text_colour(ampm_numberpicker);
    }
    private void set_numberpicker_text_colour(NumberPicker number_picker){
        final int count = number_picker.getChildCount();
        final int color = getResources().getColor(R.color.white);

        for(int i = 0; i < count; i++){
            View child = number_picker.getChildAt(i);

            try{
                Field wheelpaint_field = number_picker.getClass().getDeclaredField("mSelectorWheelPaint");
                wheelpaint_field.setAccessible(true);

                ((Paint)wheelpaint_field.get(number_picker)).setColor(color);
                ((EditText)child).setTextColor(color);
                number_picker.invalidate();
            }
            catch(NoSuchFieldException e){
               // Log.w("setNumberPickerTextColor", e);
            }
            catch(IllegalAccessException e){
                //Log.w("setNumberPickerTextColor", e);
            }
            catch(IllegalArgumentException e){
                //Log.w("setNumberPickerTextColor", e);
            }
        }
    }
    public boolean validateName()
    {
        if (patientName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_empty));
            requestFocus(patientName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    public boolean validateIssue()
    {
        if (medicalIssue.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_empty));
            requestFocus(medicalIssue);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.name:
                    validateName();
                    break;
                case R.id.issue:
                    validateIssue();
                    break;
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
