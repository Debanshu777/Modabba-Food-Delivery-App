package com.example.modabba;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.modabba.SessionManagement.SessionManagement;
import com.example.modabba.Utils.PassingData;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ProgressDialog progressDialog;
    private GoogleMap mGoogleMap;
    private Geocoder geocoder;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private LocationCallback locationCallback;
    private View mapView;
    private static final String TAG  = MapActivity.class.getSimpleName();
    private final float DEFAULT_ZOOM = 19;

    private FirebaseAuth firebaseAuth;
    private SessionManagement sessionManagement;
    private FirebaseFirestore db;
    private ImageView moveMarker;
    private int callingActivity;
    private TextInputEditText location,landmark;
    private ChipGroup chipGroup;
    private  String sessionId;
    private List<Address> addressList;
    private LatLng finalLatLng;
    private MaterialButton save;
    private String addressType;
    private PassingData data;
    private ImageView iv_lines;
    private AnimatedVectorDrawable avd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        callingActivity = getIntent().getIntExtra("callingActivity",0000);
        sessionId=getIntent().getStringExtra("Sessionid");

        init();

        avd.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                iv_lines.post(new Runnable() {
                    @Override
                    public void run() {
                        avd.start();
                    }
                });
            }
        });
        avd.start();


        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {

                switch (i){

                    case R.id.home : addressType = "Home";
                        break;
                    case R.id.work : addressType = "Work";
                        break;
                    case R.id.other : addressType = "Other";
                        break;

                }
                save.setBackgroundColor(getResources().getColor(R.color.buttonPressed));
                save.setTextColor(Color.WHITE);
                save.setEnabled(true);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (callingActivity){

                    case ActivityConstants.SignUpActivity:
                        signUpUser();
                        break;
                    case 002:
                        updateAddress();
                        break;
                }

            }
        });

    }
    public void updateAddress()
        {
            String city = addressList.get(0).getLocality();
            Map<String,Object> details  = new HashMap<>();
            details.put("city",addressList.get(0).getLocality());
            details.put("pincode",addressList.get(0).getPostalCode());
            details.put("geopoint", new GeoPoint(finalLatLng.latitude,finalLatLng.longitude));
            details.put("completeAddress",addressList.get(0).getAddressLine(0));
            details.put("landmark",landmark.getText().toString());


            Map<String,Map<String,Object>> userAddress = new HashMap<>();
            userAddress.put(addressType,details);

            DocumentReference docref=db.collection("users").document(sessionId);
            docref.update("address",userAddress).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(MapActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MapActivity.this, "not updated", Toast.LENGTH_SHORT).show();
                }
            });
            if(city.equals("Bhubaneswar")) {
                docref.update("servicable", "Servicable");
            }else {
                docref.update("servicable", "Not Servicable");
            }

        }
    public void signUpUser(){

        String city = addressList.get(0).getLocality();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());


        //User Address
        Map<String,Object> details  = new HashMap<>();
        details.put("city",addressList.get(0).getLocality());
        details.put("pincode",addressList.get(0).getPostalCode());
        details.put("geopoint", new GeoPoint(finalLatLng.latitude,finalLatLng.longitude));
        details.put("completeAddress",addressList.get(0).getAddressLine(0));
        details.put("landmark",landmark.getText().toString());


        Map<String,Map<String,Object>> userAddress = new HashMap<>();
        userAddress.put(addressType,details);

        data = getIntent().getParcelableExtra("object");

        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Registering User");
        progressDialog.show();

        Map<String, Object> user = new HashMap<>();
        user.put("username",data.getName());
        user.put("email",data.getEmail());
        user.put("password",data.getPassword());
        user.put("primaryNumber",data.getPhone());
        user.put("registration_Date",currentDate+" "+currentTime);
        user.put("alternateNumber",data.getAltphone());
        user.put("address",userAddress);
        user.put("wallet",00);


        //check for serviceability
        if(city.equals("Bhubaneswar")) {
            user.put("servicable", "Servicable");
        }else {
            user.put("servicable", "Not Servicable");
        }

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        progressDialog.dismiss();


                        sessionManagement.createLoginSession(data.getPhone(),data.getEmail(),data.getName(),documentReference.getId());
                        startActivity(new Intent(MapActivity.this,MainActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getApplicationContext(),"Please try after some time",Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void init() {

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        sessionManagement = new SessionManagement(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapActivity.this);
        moveMarker = findViewById(R.id.text3);
        location = findViewById(R.id.maps_details_address);
        landmark = findViewById(R.id.landmark);
        chipGroup = findViewById(R.id.chipGroup);
        save = findViewById(R.id.save);
        iv_lines=findViewById(R.id.iv_line);
        avd= (AnimatedVectorDrawable) iv_lines.getBackground();
        progressDialog = new ProgressDialog(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

        if(mapView!=null && mapView.findViewById(Integer.parseInt("1"))!=null){

            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 40, 45);

        }
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof ResolvableApiException){
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(MapActivity.this, 51);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                moveMarker.setVisibility(View.INVISIBLE);
            }
        });
        mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                moveMarker.setVisibility(View.VISIBLE);
                finalLatLng= mGoogleMap.getCameraPosition().target;

                try {
                    getLocationString(finalLatLng);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 51) {
            if(resultCode == RESULT_OK) {
                getDeviceLocation();
            }
        }
    }
    private void getLocationString(LatLng finalLatLng) throws IOException {

        geocoder = new Geocoder(this, Locale.getDefault());
        addressList = geocoder.getFromLocation(finalLatLng.latitude,finalLatLng.longitude,1);


        if(!addressList.isEmpty()) {

            Address ad = addressList.get(0);

            String city = ad.getLocality();
            String state = ad.getAdminArea();
            String a = ad.getAddressLine(0);

            location.setText(a);
            avd.stop();

//            if(!state.equals("Odisha")){
//                save.setText("Notify Availability");
//            }
//            else if(!city.equals("Bhubaneswar")){
//                save.setText("Notify Availability");
//            }
//            else
        }
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation(){
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if(task.isSuccessful()){
                            mLastKnownLocation = task.getResult();
                            if(mLastKnownLocation != null){
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()),DEFAULT_ZOOM));

                            } else {
                                final LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(10000);
                                locationRequest.setFastestInterval(5000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                locationCallback = new LocationCallback(){
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        if(locationResult == null) {
                                            return;
                                        }
                                        mLastKnownLocation = locationResult.getLastLocation();
                                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                    }
                                };
                                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

                            }
                        } else {
                            Toast.makeText(MapActivity.this, "unable to get last location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
