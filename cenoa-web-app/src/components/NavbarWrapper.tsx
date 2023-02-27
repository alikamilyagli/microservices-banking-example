import { Fragment, useState, useEffect } from 'react'
import { useRouter } from 'next/router'
import Link from 'next/link'
import {
	Navbar,
	Nav,
	Container,
	Form,
	Button
} from 'react-bootstrap'

import Auth from '@aws-amplify/auth';
import useMounted from '../hooks/useMounted';
import useUserStatus from '../hooks/useUserStatus';

const NavbarWrapper = ({authState}: any) => {
  const [expandedMenu, setExpandedMenu] = useState(false)
  const router = useRouter();
  const hasMounted = useMounted();
  const userStatus = useUserStatus();
  const isLoggedIn = (null !== userStatus);

  const handleSignOut = async () => {
    await Auth.signOut();
    router.push("/")
  };

  return (
  		<>
  		  <Navbar
          onToggle={(collapsed) => setExpandedMenu(collapsed)}
          expanded={expandedMenu}
          expand="lg"
          className="navbar p-2 navbar-default"
        >
          <Container fluid className="px-0 ps-2">
            <Link href="/" passHref legacyBehavior>
              <Navbar.Brand className={'mt-3 text-secondary fs-3'}>
                <p>Cenoa Bank</p>
              </Navbar.Brand>
            </Link>
            <Navbar.Toggle aria-controls="basic-navbar-nav">
              <span className="icon-bar top-bar mt-0"></span>
              <span className="icon-bar middle-bar"></span>
              <span className="icon-bar bottom-bar"></span>
            </Navbar.Toggle>
            <Navbar.Collapse id="basic-navbar-nav">
              {hasMounted && isLoggedIn ? (
                <Nav className="ms-6 gap-md-6">
                  <Nav.Link
                    key={'nav-0'}
                    as={Link}
                    href={'/accounts'}>
                    Accounts
                  </Nav.Link>
                  <Nav.Link
                    key={'nav-1'}
                    as={Link}
                    href={'/history'}>
                    Transaction History
                  </Nav.Link>
                </Nav>
              ) :
                null
              }
              <div className='ms-auto d-flex align-items-center'>
                <Nav className="navbar-nav navbar-right-wrap ms-auto d-flex nav-top-wrap">
                  {hasMounted && isLoggedIn ?
                    <span className={'ms-auto mt-3 mt-lg-0'}>
                      <Nav.Link
                        href="#"
                        onClick={handleSignOut}
                        bsPrefix="btn"
                        className="btn btn-secondary shadow-sm me-2"
                      >
                        Sign Out
                      </Nav.Link>
                    </span>
                  :
                    null
                  }
                </Nav>
              </div>
            </Navbar.Collapse>
          </Container>
        </Navbar>
  		</>
  )

}

export default NavbarWrapper
