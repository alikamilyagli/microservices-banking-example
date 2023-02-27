import React, { ReactNode } from "react";

import NavbarWrapper from './NavbarWrapper'
import FooterWrapper from './FooterWrapper'

interface Props {
    children?: ReactNode
}

const Layout = ({ children }: Props) => {

  return (
    <>
      <NavbarWrapper />
      <main>
        {children}
      </main>
      <FooterWrapper />
    </>
  )

}

export default Layout