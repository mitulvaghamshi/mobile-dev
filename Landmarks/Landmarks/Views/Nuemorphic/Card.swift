//
//  Card.swift
//  Landmarks
//
//  Created by mitulvaghamshi on 2021-11-25.
//

import SwiftUI

struct Card: View {
    var body: some View {
        VStack {
            Text("Hello World!")
                .font(.title).bold()
                .foregroundColor(.white)
                .shadow(radius: 10)
        }
        .frame(width: 300, height: 400)
        .background(.blue)
        .cornerRadius(16)
        .shadow(color: .blue.opacity(0.3), radius: 20, x: 0, y: 10)
        .shadow(color: .blue.opacity(0.2), radius: 5, x: 0, y: 2)
    }
}

struct Card_Previews: PreviewProvider {
    static var previews: some View {
        Card()
    }
}
