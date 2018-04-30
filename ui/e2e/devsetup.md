
1. npm install govuk_frontend_toolkit
2. npm install govuk-elements-sass
3. Open angular-cli.json and make the following entry under "styles"
      "stylePreprocessorOptions": {
        "includePaths": [
          "../node_modules/govuk_frontend_toolkit/stylesheets",
          "../node_modules/govuk-elements-sass/public/sass"
        ]
      },
