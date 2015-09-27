package pe.area51.googlemaps;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationChangeListener {

    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getMapAsync(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMyLocationChangeListener(this);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        this.googleMap = googleMap;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //Si devuelve true, la cámara no se moverá a la ubicación. False para el caso contrario.
        return true;
    }

    @Override
    public void onMyLocationChange(Location location) {

    }

    public void moveToLocation(final Location location, final boolean animate) {
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        final CameraPosition cameraPosition = CameraPosition.builder().target(latLng).build();
        final CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        if (animate) {
            this.googleMap.animateCamera(cameraUpdate);
        } else {
            this.googleMap.moveCamera(cameraUpdate);
        }
    }
}
