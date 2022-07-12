//
//  FavoriteButton.swift
//  Landmarks
//
//  Created by mitulvaghamshi on 2021-07-14.
//

import SwiftUI

struct FavoriteButton: View {
  @Binding var isSet: Bool
  var body: some View {
    Button(action: {
      isSet.toggle()
    }, label: {
      Image(systemName: isSet ? "star.fill" : "star").foregroundColor(isSet ? .yellow : .primary)
    })
  }
}

struct FavoriteButton_Previews: PreviewProvider {
  static var previews: some View {
    FavoriteButton(isSet: .constant(false)).preferredColorScheme(.dark)
  }
}
