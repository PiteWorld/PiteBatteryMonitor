package com.pite.batterymonitor;

import com.ab.activity.AbActivity;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.overlay.DrivingRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.dynamic.RouteSearchWrapper;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.WalkRouteResult;
import com.umeng.message.PushAgent;

import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 2D��ͼ��ʾ ,RouteSearch.OnRouteSearchListener
 */
public class PostionActivity extends AbActivity implements LocationSource, OnRouteSearchListener {
	private View noView = null;
	private MapView mapView = null; // �Զ����ͼView
	private AMap aMap;
	private OnLocationChangedListener mListener;
	private LatLonPoint startPoint;
	private LatLonPoint endPoint;

	private AMapLocationClient mlocationClient = null;
	private AMapLocationClientOption mLocationOption;


	// ·�߹滮
	private RouteSearch routeSearch;
	private RouteSearch.FromAndTo fromAndTo;// ��ʼ����յ�ľ�γ��
	// ��ͼ�ľ�γ��
	private String longitude = null;
	private String latitude = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(mInflater.inflate(R.layout.activity_postion, null));
		mapView = (MapView) this.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// �˷���������д
		noView = LayoutInflater.from(this).inflate(R.layout.no_data, null);
		PushAgent.getInstance(PostionActivity.this).onAppStart();
		this.setTitleText(R.string.Battery_postion);
		this.setLogo(R.drawable.button_selector_back);
		this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
		this.setLogoLine(R.drawable.line);
		View view = mInflater.inflate(R.layout.home_btn, null);// �������ұ�
		this.addRightView(view);
		Button btnHome = (Button) this.findViewById(R.id.homeBtn);
		// ȡֵ
		// ����
		longitude = getIntent().getStringExtra("longitude");
		// γ��
		latitude = getIntent().getStringExtra("latitude");
		endPoint = new LatLonPoint(Double.valueOf(latitude), Double.valueOf(longitude));
		//endPoint = new LatLonPoint(22.490282,113.900734);
		init();
		btnHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PostionActivity.this, MenuActivity.class));
				PostionActivity.this.finish();
			}
		});
	}

	/**
	 * ��ʼ��AMap����
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
		}
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.strokeColor(Color.BLUE);// ����Բ�εı߿���ɫ
		myLocationStyle.radiusFillColor(Color.argb(50,0,0,180));// ����Բ�ε������ɫargb(100, 0, 0, 0)
		myLocationStyle.strokeWidth(0.5f);// ����Բ�εı߿��ϸ
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setLocationSource(this);// ���ö�λ����
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// ����Ĭ�϶�λ��ť�Ƿ���ʾ
		aMap.getUiSettings().setScaleControlsEnabled(true); //���ñ����߹����Ƿ����
		//aMap.getUiSettings().setCompassEnabled(true);// ����ָ����
		aMap.setMyLocationEnabled(true);// ����Ϊtrue��ʾ��ʾ��λ�㲢�ɴ�����λ��false��ʾ���ض�λ�㲢���ɴ�����λ��Ĭ����false
		//moveToPostion(mapView, new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)));
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();

	}

	/**
	 * ����������д
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onDestroy() {
		Log.e("tag", "onDestroy");
		mapView.onDestroy();
		super.onDestroy();
	}

	/**
	 * �ƶ���ָ��λ��
	 */
	public void moveToPostion(View v, LatLng postion) {
		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(postion, 10));
		aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).position(postion).draggable(true)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.detail)));
		aMap.invalidate();// ˢ�µ�ͼ
	}

	// ��λ�ɹ���ص�����
	AMapLocationListener amaplocationlistener = new AMapLocationListener() {
			@Override
			public void onLocationChanged(AMapLocation amapLocation) {
				if (mListener != null && amapLocation != null) {
					if (amapLocation != null && amapLocation.getErrorCode() == 0) {
						aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
						// ��λ�ɹ��ص���Ϣ�����������Ϣ
						amapLocation.getLocationType();// ��ȡ��ǰ��λ�����Դ�������綨λ����������λ���ͱ�
						amapLocation.getLatitude();// ��ȡγ��
						amapLocation.getLongitude();// ��ȡ����
						aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
						startPoint = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
						mListener.onLocationChanged(amapLocation);// ��ʾϵͳС����
						fromAndTo = new RouteSearch.FromAndTo(startPoint, endPoint);// ʵ����FromAndTo
						Log.e("tag", amapLocation.getLatitude() +"*********"+amapLocation.getLongitude());
						RouteDesign();
						mlocationClient.unRegisterLocationListener(amaplocationlistener);
					} else {
						Log.e("AmapErr", "Location ERR:" + amapLocation.getErrorCode());
					}
			   }
		 }
	};
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(getApplicationContext());
			mLocationOption = new AMapLocationClientOption();
			// ���ö�λ���� //���ö�λ�ص�����
			mlocationClient.setLocationListener(amaplocationlistener);
			// ����Ϊ�߾��ȶ�λģʽ
			mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
			 mLocationOption.setOnceLocation(false);
			 mLocationOption.setMockEnable(false);
			// ���ö�λ����
			mlocationClient.setLocationOption(mLocationOption);
			// �˷���Ϊÿ���̶�ʱ��ᷢ��һ�ζ�λ����Ϊ�˼��ٵ������Ļ������������ģ�
			// ע�����ú��ʵĶ�λʱ��ļ������С���֧��Ϊ2000ms���������ں���ʱ�����stopLocation()������ȡ����λ����
			// �ڶ�λ�������ں��ʵ��������ڵ���onDestroy()����
			// �ڵ��ζ�λ����£���λ���۳ɹ���񣬶��������stopLocation()�����Ƴ����󣬶�λsdk�ڲ����Ƴ�
			Log.e("tag", "-------");
			mlocationClient.startLocation();// ������λ
		}
	}
	//�г�·�����
	private void RouteDesign() {
		routeSearch = new RouteSearch(this);
		routeSearch.setRouteSearchListener(this);
		// fromAndTo����·���滮�������յ㣬drivingMode��ʾ�ݳ�ģʽ
		// ������������ʾ;���㣨���֧��16���������ĸ�������ʾ�����������֧��32�����������������ʾ���õ�·
		DriveRouteQuery query = new DriveRouteQuery(fromAndTo, RouteSearch.DrivingShortDistance, null, null, "");
		routeSearch.calculateDriveRouteAsyn(query);//������·
	}

	@Override
	public void deactivate() {
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}
	//����·�߻ص�����
	@Override
	public void onBusRouteSearched(BusRouteResult arg0, int arg1) {

	}
	//�ݳ�·�߻ص�����
	@Override
	public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
		DrivePath drivePath = driveRouteResult.getPaths().get(0);//ȡ����һ��·��
		aMap.clear();

		DrivingRouteOverlay routeOverlay = new DrivingRouteOverlay(this, aMap,
				drivePath, driveRouteResult.getStartPos(),
				driveRouteResult.getTargetPos());
		routeOverlay.removeFromMap();
		routeOverlay.addToMap();
		routeOverlay.setNodeIconVisibility(false);
		routeOverlay.setThroughPointIconVisibility(false);
		routeOverlay.zoomToSpan();
	}
	//����·�߻ص�����
	@Override
	public void onWalkRouteSearched(WalkRouteResult arg0, int arg1) {

	}
}
