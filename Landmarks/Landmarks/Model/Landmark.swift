//
//  Landmark.swift
//  Landmarks
//
//  Created by mitulvaghamshi on 2021-07-14.
//

import SwiftUI
import Foundation
import CoreLocation

struct Landmark: Hashable, Codable, Identifiable {
  var id: Int
  var name: String
  var park: String
  var state: String
  var isFavorite: Bool
  var description: String
  var isFeatured: Bool

  var category: Category

  enum Category: String, CaseIterable, Codable {
    case lakes = "Lakes"
    case rivers = "Rivers"
    case mountains = "Mountains"
  }

  private var imageName: String
  var image: Image {
    Image(imageName)
  }

  var featureImage: Image? {
    isFeatured ? Image(imageName + "_feature") : nil
  }
  
  private var coordinates: Coordinates
  
  var locationCoordinate: CLLocationCoordinate2D {
    CLLocationCoordinate2D(
      latitude: coordinates.latitude,
      longitude: coordinates.longitude
    )
  }
  
  struct Coordinates: Hashable, Codable {
    var latitude: Double
    var longitude: Double
  }
}
