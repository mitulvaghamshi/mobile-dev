//
//  CategoryRow.swift
//  Landmarks
//
//  Created by mitulvaghamshi on 2021-10-04.
//

import SwiftUI

struct CategoryRow: View {
  var categoryName: String
  var items: [Landmark]

  var body: some View {
    VStack(alignment: .leading, spacing: 0) {
      Text(categoryName)
        .font(.headline)
        .padding(.top)
        .padding(.leading, 5)

      ScrollView(.horizontal, showsIndicators: false) {
        HStack(alignment: .top, spacing: 0) {
          ForEach(items) { landmark in
            NavigationLink(destination: LandmarkDetail(landmark: landmark)) {
              CategoryItem(landmark: landmark)
            }
          }
        }
      }.frame(height: 185)
    }
  }
}

struct CategoryRow_Previews: PreviewProvider {
  static var landmarks = ModelData().landmarks

  static var previews: some View {
    CategoryRow(
      categoryName: landmarks.first!.category.rawValue,
      items: Array(landmarks.prefix(4))
    )
  }
}
