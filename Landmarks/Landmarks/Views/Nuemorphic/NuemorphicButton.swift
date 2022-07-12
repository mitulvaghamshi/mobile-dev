//
//  NuemorphicButton.swift
//  Landmarks
//
//  Created by mitulvaghamshi on 2021-11-25.
//

import SwiftUI

struct NuemorphicButton: View {
    var body: some View {
        VStack { OuterShadow() }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color(#colorLiteral(red: 0.8980392157, green: 0.9333333333, blue: 1, alpha: 1)))
        .ignoresSafeArea(.all)
    }
}

struct OuterShadow: View {
    var body: some View {
        NuemorphicText()
            .shadow(color: Color(#colorLiteral(red: 0.7608050108, green: 0.8164883852, blue: 0.9259157777, alpha: 1)), radius: 20, x: 20, y: 20)
            .shadow(color: Color(#colorLiteral(red: 1, green: 1, blue: 1, alpha: 1)), radius: 20, x: -20, y: -20)
    }
}

struct NuemorphicText: View {
    var body: some View {
        Text("Button")
            .font(.system(size: 20, weight: .semibold, design: .rounded))
            .frame(width: 150, height: 70)
            .background(InnerShadow())
            .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
    }
}

struct InnerShadow: View {
    var body: some View {
        ZStack{
            Color(#colorLiteral(red: 0.7608050108, green: 0.8164883852, blue: 0.9259157777, alpha: 1))
            RoundedRectangle(cornerRadius: 16, style: .continuous)
                .foregroundColor(.white)
                .blur(radius: 4)
                .offset(x: -8, y: -8)
            // #colorLiteral(red: 0.9019607843, green: 0.9294117647, blue: 0.9882352941, alpha: 1
            RoundedRectangle(cornerRadius: 16, style: .continuous)
                .fill(LinearGradient(gradient: Gradient(colors: [Color(#colorLiteral(red: 0.9019607843, green: 0.9294117647, blue: 0.9882352941, alpha: 1)), Color.white]), startPoint: .topLeading, endPoint: .bottomTrailing))
                .padding(2)
                .blur(radius: 2)
        }
    }
}

struct NuemorphicButton_Previews: PreviewProvider {
    static var previews: some View {
        NuemorphicButton()
    }
}
