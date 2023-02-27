import { Head, Html, Main, NextScript } from "next/document";

export default function Document() {
	return (
		<Html>
			<Head>
			  <link rel='preconnect' href='https://fonts.googleapis.com' />
        <link rel='preconnect' href='https://fonts.gstatic.com' />
        <link
          rel='stylesheet'
          href='https://fonts.googleapis.com/css?family=Inter:200,300,400,500,600,700,800,900&display=swap'
        />
			</Head>
			<body>
				<Main />
				<NextScript />
			</body>
		</Html>
	);
}
