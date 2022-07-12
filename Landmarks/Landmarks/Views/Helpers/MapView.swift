//
//  MapView.swift
//  Landmarks
//
//  Created by mitulvaghamshi on 2021-07-14.
//

import SwiftUI
import MapKit

struct MapView: View {
  var coordinate: CLLocationCoordinate2D

  //  @State private var region = MKCoordinateRegion(
  //    center: CLLocationCoordinate2DMake(34.011_268, -116.166_868),
  //    span: MKCoordinateSpan(latitudeDelta: 0.2, longitudeDelta: 0.2)
  //  )

  @AppStorage("MapView.zoom")
  private var zoom: Zoom = .medium

  enum Zoom: String, CaseIterable, Identifiable {
    case near = "Near"
    case medium = "Medium"
    case far = "Far"

    var id: Zoom { return self }
  }

  var delta: CLLocationDegrees {
    switch zoom {
    case .near: return 0.02
    case .medium: return 0.2
    case .far: return 2
    }
  }

  var region: MKCoordinateRegion {
    MKCoordinateRegion(
      center: coordinate,
      span: MKCoordinateSpan(
        latitudeDelta: delta,
        longitudeDelta: delta
      )
    )
  }
  
  var body: some View {
    //    Map(coordinateRegion: $region)
    //      .onAppear {setRegion(coordinate)}
    Map(coordinateRegion: .constant(region))
  }
  
  //  private func setRegion(_ coordinates: CLLocationCoordinate2D) {
  //    region = MKCoordinateRegion(
  //      center: coordinates,
  //      span: MKCoordinateSpan(latitudeDelta: delta, longitudeDelta: delta)
  //    )
  //  }
}

struct MapView_Previews: PreviewProvider {
  static var previews: some View {
    MapView(coordinate: ModelData().landmarks.first!.locationCoordinate).environmentObject(ModelData())
  }
}
