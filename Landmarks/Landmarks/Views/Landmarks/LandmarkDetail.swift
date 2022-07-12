//
//  LandmarkDetail.swift
//  Landmarks
//
//  Created by mitulvaghamshi on 2021-07-14.
//

import SwiftUI

struct LandmarkDetail: View {
  @EnvironmentObject var modelData: ModelData
  var landmark: Landmark
  
  var landmarkIndex: Int {
    modelData.landmarks.firstIndex(where: { landmark.id == $0.id })!
  }
  
  var body: some View {
    ScrollView {
      MapView(coordinate: landmark.locationCoordinate).ignoresSafeArea(edges: .top).frame(height: 300)
      CircleImage(image: landmark.image).offset(y: -130).padding(.bottom, -130)
      VStack(alignment: .leading) {
        HStack {
          Text(landmark.name).font(.system(size: 30, weight: .black, design: .rounded))
          Spacer()
          FavoriteButton(isSet: $modelData.landmarks[landmarkIndex].isFavorite)
        }
        HStack {
          Text(landmark.park)
          Spacer()
          Text(landmark.state)
        }.font(.subheadline).foregroundColor(.secondary)
        Divider()
        Text("About \(landmark.name)").font(.title3).padding(.bottom)
        Text(landmark.description).font(.footnote)
      }.padding()
    }.navigationTitle(landmark.name).navigationBarTitleDisplayMode(.inline)
  }
}

struct LandmarkDetail_Previews: PreviewProvider {
  static var previews: some View {
    LandmarkDetail(landmark: ModelData().landmarks.first!).environmentObject(ModelData())
  }
}
