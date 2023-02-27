import type { AppProps } from "next/app";
import SSRProvider from 'react-bootstrap/SSRProvider';
import "../styles/global.scss";
import Layout from '../components/Layout'

import Amplify from '@aws-amplify/core';
import awsExports from "../aws-exports"

Amplify.configure({...awsExports, ssr: true});

export default function App({ Component, pageProps }: AppProps) {
	return (
	  <SSRProvider>
      <Layout>
        <Component {...pageProps} />
      </Layout>
	  </SSRProvider>
	);
}
