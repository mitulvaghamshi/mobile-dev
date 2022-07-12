//
//  Profile.swift
//  Landmarks
//
//  Created by mitulvaghamshi on 2021-10-04.
//

import Foundation

struct Profile {
  var username: String
  var prefersNotifications = true
  var seasonalPhoto = Season.winter
  var goalDate = Date()

  static let `default` = Profile(username: "Mitul")

  enum Season: String, CaseIterable, Codable {
    case spring = "🌷"
    case summer = "🌞"
    case autumn = "🍂"
    case winter = "☃️"

    var id: String { self.rawValue }
  }
}
