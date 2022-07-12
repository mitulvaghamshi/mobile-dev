//
//  Utils.swift
//  Landmarks
//
//  Created by mitulvaghamshi on 2021-09-30.
//

import SwiftUI

extension AnyTransition {
  static var moveAndFade: AnyTransition {
    let insertion = AnyTransition.move(edge: .trailing)
      .combined(with: .opacity)
    let removal = AnyTransition.scale
      .combined(with: .opacity)
    return .asymmetric(insertion: insertion, removal: removal)
  }
}

extension Animation {
  static func ripple(_ index: Int = 1) -> Animation {
    Animation.spring(dampingFraction: 0.5)
      .speed(2)
      .delay(0.03 * Double(index))
  }
}
