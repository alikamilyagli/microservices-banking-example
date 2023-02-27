import Head from "next/head";
import React, { ReactNode, Fragment, useState, useEffect } from 'react';

import {
	Col,
	Row,
	Card,
	Container
} from 'react-bootstrap';

import '@aws-amplify/ui-react/styles.css'
import Auth from '@aws-amplify/auth';
import useMounted from '../hooks/useMounted';
import { withAuthenticator } from "@aws-amplify/ui-react";
import { CognitoUserInterface } from '@aws-amplify/ui-components';

function Home({user}: CognitoUserInterface) {

  const hasMounted = useMounted();
  const [accessToken, setAccessToken] = useState(null);

  useEffect(() => {
    const accessToken = user.getSignInUserSession().getAccessToken().getJwtToken()
    setAccessToken(accessToken)
  }, [user]);

	return (
		<Fragment>
			<Head>
				<title>Cenoa Bank | Homepage</title>
			</Head>

      <section className="py-10">
        <Container>
          <Row className="justify-content-center">
            <Col xs={12}>
              <Card>
                <Card.Body className="p-6">
                  {hasMounted && null != user ?
                     <h1>hello {user.attributes.email}</h1>
                   : null }

                  <p>Welcome to Cenoa Bank</p>

                  {hasMounted && null != accessToken ? (
                    <Fragment>
                      <p></p>
                      <p>You can use the accessToken below in postman</p>
                      <p>{accessToken}</p>
                    </Fragment>
                  ) : null }

                </Card.Body>
              </Card>
            </Col>
          </Row>
        </Container>
      </section>

		</Fragment>
	);
}

export default withAuthenticator(Home)
