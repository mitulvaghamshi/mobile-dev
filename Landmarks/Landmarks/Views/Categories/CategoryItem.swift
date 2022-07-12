//
//  CategoryItem.swift
//  Landmarks
//
//  Created by mitulvaghamshi on 2021-10-04.
//

import SwiftUI

struct CategoryItem: View {
  var landmark: Landmark

  var body: some View {
    VStack(alignment: .leading) {
      landmark.image
        .renderingMode(.original)
        .resizable()
        .frame(width: 155, height: 155)
        .cornerRadius(5)

      Text(landmark.name)
        .foregroundColor(.primary)
        .font(.caption)
    }.padding(5)
  }
}

struct CategoryItem_Previews: PreviewProvider {
  static var landmarks = ModelData().landmarks

  static var previews: some View {
    CategoryItem(landmark: landmarks.first!)
  }
}
